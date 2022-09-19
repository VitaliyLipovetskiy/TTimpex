package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
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
        Collection<EmployeeDaysOffTo> employeeDaysOffToList = new ArrayList<>();
        Map<Integer, List<Worked>> allEmployeeWorked = workedRepository.getAllEmployeeBetween(startDate, endDate);
        Map<Integer, Map<LocalDate, DayOff>> allEmployeeDayOff = daysOffRepository.getAllEmployeeBetween(startDate, endDate);

//        allEmployeeWorked.forEach((i, v) -> System.out.println(i + "=>" + v));
//        System.out.println("=======");
//        allEmployeeDayOff.forEach((i, v) -> System.out.println(i + "=>" + v));
//        System.out.println("=======");

        new ConcurrentHashMap<>(allEmployeeDayOff).forEach((employeeId, v) -> {
            List<Worked> workers = allEmployeeWorked.get(employeeId);
            if (workers != null) {
//                System.out.println("1=" + employeeId + "=>" + workers);
                v.values().stream().sorted(Comparator.comparing(DayOff::getDate))
                        .forEach(dayOff ->
                    workers.stream()
                            .sorted(Comparator.comparing(Worked::getRecruitment))
                            .forEach(worked -> {
                                // Recruitment - прием на работу
                                // Dismissal   - увольнение
                                if (dayOff.getWorked() == null || !dayOff.getWorked()) {
                                    Map<LocalDate, DayOff> dayOffMap = new ConcurrentHashMap<>(allEmployeeDayOff.get(employeeId));
                                    dayOffMap.get(dayOff.getDate()).setWorked(worked.getRecruitment() != null && worked.getRecruitment().isBefore(dayOff.getDate().plusDays(1))
                                            && (worked.getDismissal() == null || worked.getDismissal().isAfter(dayOff.getDate().minusDays(1))));
                                    allEmployeeDayOff.put(employeeId, dayOffMap);
                                }
                            })
                );
            }
        });

//        allEmployeeDayOff.forEach((i, v) -> System.out.println(i + "=>" + v));
//        System.out.println("=======");

        List<Employee> employees = employeeRepository.getAll();
        employees.forEach(employee -> {
            if (allEmployeeWorked.containsKey(employee.getId())) {
                Worked last = allEmployeeWorked.get(employee.getId()).stream()
                        .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                        .findFirst().orElse(new Worked());
                EmployeeTo employeeTo = Util.getEmployeeTo(employee, last);
                List<DayOff> daysOff = allEmployeeDayOff.get(employee.getId()).values().stream()
                        .sorted(Comparator.comparing(DayOff::getDate)).toList();
                employeeDaysOffToList.add(new EmployeeDaysOffTo(employeeTo, daysOff));
            }
        });

        return employeeDaysOffToList;
    }
}
