package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
import com.lvv.ttimpex2.to.DayOffTo;
import com.lvv.ttimpex2.to.EmployeeDaysOffTo;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.utils.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class DayOffService {

    private final DaysOffRepository daysOffRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkedRepository workedRepository;

    public DayOffService(DaysOffRepository daysOffRepository, EmployeeRepository employeeRepository, WorkedRepository workedRepository) {
        this.daysOffRepository = daysOffRepository;
        this.employeeRepository = employeeRepository;
        this.workedRepository = workedRepository;
    }

    public void update(int employeeId, DayOff dayOff) {
        daysOffRepository.update(employeeId, dayOff);
    }

    public Collection<EmployeeDaysOffTo> getAllEmployeesForWithHolidays(LocalDate startDate, LocalDate endDate) {
        // карта сотрудников со списком приема и увольнения
        Map<Integer, List<Worked>> allEmployeeWorked = workedRepository.getAllEmployeeBetween(startDate, endDate);
        // карта сотрудников с картой выходных дней
        Map<Integer, Map<LocalDate, DayOffTo>> allEmployeeDayOffTo = daysOffRepository.getAllEmployeeBetween(startDate, endDate);

        allEmployeeWorked.forEach((employeeId, workers) -> {
            if (!allEmployeeDayOffTo.containsKey(employeeId)) {
                Map<LocalDate, DayOffTo> dasOffTo = new ConcurrentHashMap<>();
                for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                    dasOffTo.put(date, new DayOffTo(date));
                }
                allEmployeeDayOffTo.put(employeeId, dasOffTo);
            }
            Map<LocalDate, DayOffTo> datesDayOffTo = allEmployeeDayOffTo.get(employeeId);
            workers.forEach(worked -> {
                LocalDate startLocalDate = worked.getRecruitment().isBefore(startDate) ? startDate : worked.getRecruitment();
                LocalDate endLocalDate = Objects.requireNonNullElse(worked.getDismissal(), endDate).isAfter(endDate.minusDays(1)) ? endDate : worked.getDismissal();
                for (LocalDate date = startLocalDate; date.isBefore(endLocalDate.plusDays(1)); date = date.plusDays(1)) {
                    datesDayOffTo.get(date).setWorked(true);
                }
            });

        });

        allEmployeeDayOffTo.forEach((i, v) -> {
            System.out.println(i + "=>" + v);
        });

        Collection<EmployeeDaysOffTo> employeeDaysOffToList = new ArrayList<>();
        List<Employee> employees = employeeRepository.getAll();
        employees.forEach(employee -> {
            if (allEmployeeWorked.containsKey(employee.getId())) {
                Worked last = allEmployeeWorked.get(employee.getId()).stream()
                        .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                        .findFirst().orElse(new Worked());
                EmployeeTo employeeTo = Util.getEmployeeTo(employee, last);
                List<DayOffTo> daysOff = allEmployeeDayOffTo.get(employee.getId()).values().stream()
                        .sorted(Comparator.comparing(DayOffTo::getDate)).toList();
                employeeDaysOffToList.add(new EmployeeDaysOffTo(employeeTo, daysOff));
            }
        });
        return employeeDaysOffToList;
    }
}
