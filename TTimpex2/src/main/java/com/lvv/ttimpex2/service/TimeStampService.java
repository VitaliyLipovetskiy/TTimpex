package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.Setting;
import com.lvv.ttimpex2.molel.TimeStampDate;
import com.lvv.ttimpex2.repository.SettingRepository;
import com.lvv.ttimpex2.repository.TimeStampDateRepository;
import com.lvv.ttimpex2.repository.TimeStampRepository;
import com.lvv.ttimpex2.to.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * @author Vitalii Lypovetskyi
 */
@Service
@Transactional(readOnly = true)
public class TimeStampService {

    private final TimeStampRepository timeStampRepository;
    private final TimeStampDateRepository timeStampDateRepository;
    private final SettingRepository settingRepository;

    private final DayOffService dayOffService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TimeStampService(TimeStampRepository timeStampRepository, TimeStampDateRepository timeStampDateRepository, SettingRepository settingRepository, DayOffService dayOffService) {
        this.timeStampRepository = timeStampRepository;
        this.timeStampDateRepository = timeStampDateRepository;
        this.settingRepository = settingRepository;
        this.dayOffService = dayOffService;
    }

    public DayTo getDayTo(EmployeeTo employeeTo, LocalDate localDate, DayOffTo dayOffTo) {
        Employee employee = new Employee(employeeTo);
        Map<String, LocalTime> firstAndLastByDateAndEmployee = timeStampRepository.getFirstAndLastByDateAndEmployee(localDate, employee);
        TimeStampDate timeStampDate = timeStampDateRepository.get(new EmployeeDate(
                employee,
                dayOffTo.getDate()));
        if (timeStampDate == null) {
            timeStampDate = new TimeStampDate();
        }
        // надо получить время первой отметки
        LocalTime comingAutoTime = firstAndLastByDateAndEmployee.get("first");
        // надо получить время последней отметки
        LocalTime leavingAutoTime = firstAndLastByDateAndEmployee.get("last");
        double workedOut = 0.0;

        Optional<Setting> currentSetting = settingRepository.get(localDate);
         // ??? вычислить долю сколько отработано за этот день
        LocalTime startDefaultTime = employeeTo.getStartTime();
        LocalTime endDefaultTime = employeeTo.getEndTime();
        if (currentSetting.isPresent()) {
            if (startDefaultTime == null)  {
                startDefaultTime = currentSetting.get().getStartTime();
            }
            if (endDefaultTime == null) {
                endDefaultTime = currentSetting.get().getEndTime();
            }
        }
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
        if (Boolean.TRUE.equals(employeeTo.getAccountingForHoursWorked()) && startDefaultTime != null && endDefaultTime != null) {
            workedOut = (double) leavingTime.until(comingTime, ChronoUnit.MINUTES) / endDefaultTime.until(startDefaultTime, ChronoUnit.MINUTES);
        }
        int penalty = 0;
        // ??? рассчитать штраф за эту дату
        if (timeStampDate.getPenalty() != null) {
            penalty += timeStampDate.getPenalty();
        } else if (Boolean.TRUE.equals(currentSetting.isPresent()) && Boolean.TRUE.equals(dayOffTo.getWorked()) && Boolean.FALSE.equals(dayOffTo.getDayOff())) {
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
        return new DayTo(
                dayOffTo.getDate(),
                comingAutoTime,
                timeStampDate.getComing(),
                leavingAutoTime,
                timeStampDate.getLeaving(),
                penalty,
                dayOffTo.getDayOff(),
                dayOffTo.getWorked(),
                workedOut);

    }

    public List<ReportDataTo> getFilteredForReport(LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredForReport from {} to {}", startDate, endDate);
        Collection<EmployeeDaysOffTo> allEmployeesWithDaysOff = dayOffService.getAllEmployeesWithDaysOff(startDate, endDate);
        List<ReportDataTo> result = new ArrayList<>();
        allEmployeesWithDaysOff.forEach(employeeDaysOffTo -> {
            List<DayTo> daysTo = new ArrayList<>();
            int penalty = 0;
            double workedOut = 0.0;
            Map<LocalDate, DayOffTo> mapDaysOffTo = employeeDaysOffTo.getMapDaysOffTo();
            EmployeeTo employeeTo = employeeDaysOffTo.getEmployeeTo();
            for (LocalDate localDate = startDate; localDate.isBefore(endDate); localDate = localDate.plusDays(1)) {
                DayOffTo dayOffTo = mapDaysOffTo.get(localDate);
//                if (dayOffTo == null) {
//                    System.out.println(dayOffTo);
//                    dayOffTo = new DayOffTo(localDate,
//                            null,   // ??? dayOff
//                            null);         // ??? worked
//                }

                DayTo dayTo = getDayTo(employeeTo, localDate, dayOffTo);

                daysTo.add(dayTo);
                penalty += dayTo.getPenalty();
                workedOut += dayTo.getWorkedOut();
            }
            StringBuilder employeeName = new StringBuilder(employeeTo.getName());
            if (employeeTo.getStartTime() != null || employeeTo.getEndTime() != null) {
                employeeName.append("<br>");
            }
                if (employeeTo.getStartTime() != null) {
                employeeName.append("c ").append(employeeTo.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            if (employeeTo.getEndTime() != null) {
                employeeName.append(" до ").append(employeeTo.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            ReportDataTo reportDataTo = new ReportDataTo(
                    employeeTo.getId(),
                    employeeName.toString(),
                    employeeTo.getAccountingForHoursWorked(),
                    penalty, workedOut, daysTo);
            result.add(reportDataTo);
        });
        return result;
    }

}
