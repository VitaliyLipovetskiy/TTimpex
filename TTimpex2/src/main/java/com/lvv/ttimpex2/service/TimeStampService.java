package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStampDate;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
@Transactional(readOnly = true)
public class TimeStampService {

    private final TimeStampRepository timeStampRepository;
    private final TimeStampDateRepository timeStampDateRepository;

    private final DayOffService dayOffService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TimeStampService(TimeStampRepository timeStampRepository, TimeStampDateRepository timeStampDateRepository, DayOffService dayOffService) {
        this.timeStampRepository = timeStampRepository;
        this.timeStampDateRepository = timeStampDateRepository;
        this.dayOffService = dayOffService;
    }

    public DayTo getDayTo(EmployeeTo employeeTo, LocalDate localDate, DayOffTo dayOffTo) {
        Employee employee = new Employee(employeeTo);
        Map<String, LocalTime> firstAndLastByDateAndEmployee = timeStampRepository.getFirstAndLastByDateAndEmployee(localDate, employee);
        // надо получить время первой отметки
        LocalTime comingAutoTime = firstAndLastByDateAndEmployee.get("first");
        // надо получить время последней отметки
        LocalTime leavingAutoTime = firstAndLastByDateAndEmployee.get("last");

        double workedOut = 0.0;
        if (employeeTo.getAccountingForHoursWorked()) {
            workedOut = 0.0;           // ??? вычислить долю сколько отработано за этот день


        }
        TimeStampDate timeStampDate = timeStampDateRepository.get(new EmployeeDate(
                employee,
                dayOffTo.getDate()));
        if (timeStampDate == null) {
            timeStampDate = new TimeStampDate();
        }
        int penalty = 0;
        // ??? рассчитать штраф за эту дату
        if (timeStampDate.getPenalty() != null) {
            penalty = timeStampDate.getPenalty();
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
