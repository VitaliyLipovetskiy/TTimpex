package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.*;
import com.lvv.ttimpex2.repository.*;
import com.lvv.ttimpex2.dto.*;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TimeStampService implements ParadoxHandler {
    private final TimeStampRepository timeStampRepository;
    private final TimeStampDateRepository timeStampDateRepository;
    private final EmployeeService employeeService;
    private final SettingService settingService;
    private final DayOffService dayOffService;
    private final SCodeService sCodeService;

    public DayDto getDayTo(EmployeeDto employeeDto, LocalDate localDate, DayOffAndWorkedDto dayOffAndWorkedDto) {
        Employee employee = employeeService.getEmployeeById(UUID.fromString(employeeDto.getId()));
//                Util.convertToEmployeeEntity(employeeDto);
        Map<String, LocalTime> firstAndLastByDateAndEmployee = timeStampRepository.getFirstAndLastByDateAndEmployee(employee, localDate);
        TimeStampDate timeStampDate = timeStampDateRepository.get(new EmployeeDate(
                employee,
                dayOffAndWorkedDto.getDate()));
        if (timeStampDate == null) {
            timeStampDate = new TimeStampDate();
        }
        // надо получить время первой отметки
        LocalTime comingAutoTime = firstAndLastByDateAndEmployee.get("first");
        // надо получить время последней отметки
        LocalTime leavingAutoTime = firstAndLastByDateAndEmployee.get("last");

        Optional<Setting> currentSetting = Optional.of(settingService.getSettingByDate(localDate));
         // ??? вычислить долю сколько отработано за этот день
        LocalTime startDefaultTime = employeeDto.getStartTime();
        LocalTime endDefaultTime = employeeDto.getEndTime();
        if (currentSetting.isPresent()) {
            if (startDefaultTime == null)  {
                startDefaultTime = currentSetting.get().getStartTime();
            }
            if (endDefaultTime == null) {
                endDefaultTime = currentSetting.get().getEndTime();
            }
        }
//        if ("0012".equals(employee.getCardId())) {
//            System.out.println(employee);
//        }
        LocalTime comingTime = timeStampDate.getComing();
        if (comingTime == null) {
            comingTime = Objects.requireNonNullElse(comingAutoTime, startDefaultTime);
        }
        if (startDefaultTime != null && startDefaultTime.isAfter(comingTime)) {
            comingTime = startDefaultTime;
        }
        LocalTime leavingTime = timeStampDate.getLeaving();
        if (leavingTime == null) {
            leavingTime = Objects.requireNonNullElse(leavingAutoTime, endDefaultTime);
        }
        if (endDefaultTime != null && endDefaultTime.isBefore(leavingTime)) {
            leavingTime = endDefaultTime;
        }
        double workedOut = 0.0;
        int penalty = 0;
        if (localDate.isBefore(LocalDate.now().plusDays(1))) {
            if (Boolean.TRUE.equals(employeeDto.getAccountingForHoursWorked()) && startDefaultTime != null && endDefaultTime != null
                    && Boolean.TRUE.equals(dayOffAndWorkedDto.getWorked()) &&
                    (Boolean.FALSE.equals(dayOffAndWorkedDto.getDayOff()) || ((comingAutoTime != null || timeStampDate.getComing() != null) && (leavingAutoTime != null || timeStampDate.getLeaving() != null)))) {
                workedOut = (double) leavingTime.until(comingTime, ChronoUnit.MINUTES) / endDefaultTime.until(startDefaultTime, ChronoUnit.MINUTES);
            }
            // ??? рассчитать штраф за эту дату
            if (timeStampDate.getPenalty() != null) {
                penalty += timeStampDate.getPenalty();
            } else if (Boolean.TRUE.equals(currentSetting.isPresent()) && Boolean.TRUE.equals(dayOffAndWorkedDto.getWorked()) && Boolean.FALSE.equals(dayOffAndWorkedDto.getDayOff())) {
//                if (Objects.equals(employee.getNames(), InMemoryEmployeeRepository.EMP_IVANOV_11) && localDate.getDayOfMonth() == 1) {
//                    System.out.println("null");
//                }
                if (startDefaultTime != null && comingTime.isAfter(startDefaultTime)) {
                    penalty += currentSetting.get().getPenaltyBeingLate(); // опоздание
                }
                if (endDefaultTime != null && leavingTime.isBefore(endDefaultTime)) {
                    penalty += currentSetting.get().getPenaltyEarlyCare(); // ранний уход
                }
                if (comingAutoTime == null && timeStampDate.getComing() == null && leavingAutoTime == null && timeStampDate.getLeaving() == null) {
                    penalty += currentSetting.get().getPenaltyAbsenteeism();  // прогул
                } else if (comingAutoTime == null && timeStampDate.getComing() == null || leavingAutoTime == null && timeStampDate.getLeaving() == null) {
                    // нет одной отметки
                }
            }
        }
        return new DayDto(
                dayOffAndWorkedDto.getDate(),
                comingAutoTime,
                timeStampDate.getComing(),
                leavingAutoTime,
                timeStampDate.getLeaving(),
                penalty,
                dayOffAndWorkedDto.getDayOff(),
                dayOffAndWorkedDto.getWorked(),
                workedOut);
    }

    public List<ReportDataTo> getFilteredForReport(LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredForReport from {} to {}", startDate, endDate.minusDays(1));
        List<EmployeeDaysOffTo> allEmployeesWithDaysOff = dayOffService.getAllEmployeesWithDaysOff(startDate, endDate);
        List<ReportDataTo> result = new ArrayList<>();
        allEmployeesWithDaysOff.forEach(employeeDaysOffTo -> {
            List<DayDto> daysDto = new ArrayList<>();
            int penalty = 0;
            double workedOut = 0.0;
            Map<LocalDate, DayOffAndWorkedDto> mapDaysOffTo = getMapDaysOffTo(employeeDaysOffTo.getDaysOffDto());
            EmployeeDto employeeDto = employeeDaysOffTo.getEmployeeDto();
            for (LocalDate localDate = startDate; localDate.isBefore(endDate); localDate = localDate.plusDays(1)) {
                DayOffAndWorkedDto dayOffAndWorkedDto = mapDaysOffTo.get(localDate);
//                if (dayOffTo == null) {
//                    System.out.println(dayOffTo);
//                    dayOffTo = new DayOffTo(localDate,
//                            null,   // ??? dayOff
//                            null);         // ??? worked
//                }

                DayDto dayDto = getDayTo(employeeDto, localDate, dayOffAndWorkedDto);

                daysDto.add(dayDto);
                penalty += dayDto.getPenalty();
                workedOut += dayDto.getWorkedOut();
            }
            StringBuilder employeeName = new StringBuilder();
            if (employeeDto.getLastName() != null && !employeeDto.getLastName().isEmpty()) {
                employeeName.append(employeeDto.getLastName()).append(" ");
            }
            if (employeeDto.getFirstName() != null && !employeeDto.getFirstName().isEmpty()) {
                employeeName.append(employeeDto.getFirstName());
            }
            if (employeeDto.getStartTime() != null || employeeDto.getEndTime() != null) {
                employeeName.append("<br>");
            }
                if (employeeDto.getStartTime() != null) {
                employeeName.append("c ").append(employeeDto.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            if (employeeDto.getEndTime() != null) {
                employeeName.append(" до ").append(employeeDto.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            ReportDataTo reportDataTo = new ReportDataTo(
                    employeeDto.getId(),
                    employeeName.toString(),
                    employeeDto.getAccountingForHoursWorked(),
                    penalty, workedOut, daysDto);
            result.add(reportDataTo);
        });
        return result;
    }

    @Transactional
    public void update(UpdateTimestampTo updateTimestampTo) {
        TimeStampDate timeStampDate = new TimeStampDate();
        timeStampDate.setEmployeeDate(new EmployeeDate(employeeService.getEmployeeById(updateTimestampTo.getEmployeeId()), updateTimestampTo.getDate()));
        timeStampDate.setPenalty(updateTimestampTo.getPenalty());
        timeStampDate.setComing(updateTimestampTo.getComingCorrectTime());
        timeStampDate.setLeaving(updateTimestampTo.getLeavingCorrectTime());
        timeStampDateRepository.save(timeStampDate);
    }

    @Transactional
    public TimeStamp saveTimeStamp(TimeStamp timeStamp) {
        return timeStampRepository.save(timeStamp).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to save timeStamp")
        );
    }

    @Override
    @Transactional
    public void call(Path pathDB, ResultSet resultSet, LocalDate date) throws SQLException {
        log.info("TimeStampService call");
        if (date == null) date = LocalDate.now();
        while (resultSet.next()) {
            String card = resultSet.getString("card").trim();
            Employee employee = null;
            if (!card.isEmpty()) {
                employee = sCodeService.findById(card).getEmployee();
            }
            TimeStamp timestamp = new TimeStamp(
                    null,
                    employee,
                    date,
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getInt("post"),
                    Events.getById(Math.abs(resultSet.getInt("event") - 1))
            );
            log.info("timestamp {}", timestamp);
            saveTimeStamp(timestamp);
        }
    }

    private Map<LocalDate, DayOffAndWorkedDto> getMapDaysOffTo(List<DayOffAndWorkedDto> daysOffTo) {
        return daysOffTo.stream()
                .collect(Collectors.toMap(DayOffAndWorkedDto::getDate, Function.identity()));
    }
}
