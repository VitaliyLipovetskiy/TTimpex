package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DayOffRepository;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
import com.lvv.ttimpex2.dto.DayOffAndWorkedDto;
import com.lvv.ttimpex2.dto.EmployeeDaysOffTo;
import com.lvv.ttimpex2.dto.EmployeeDto;
import com.lvv.ttimpex2.utils.Util;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class DayOffService {
    private final DayOffRepository dayOffRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkedRepository workedRepository;

    @Transactional
    public DayOff updateDayOffByEmployeeId(UUID employeeId, DayOff entity) {
        log.info("entity {}", entity);
        Employee employee = employeeRepository.getById(employeeId).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find employee")
        );
        log.info("employee {}", employee);
        EmployeeDate employeeDate = new EmployeeDate(employee, entity.getEmployeeDate().getDate());
        log.info("employeeDate {}", employeeDate);
        DayOff dayOff = dayOffRepository.findById(employeeDate).orElse(
                new DayOff(new EmployeeDate(employee, entity.getEmployeeDate().getDate()), entity.getDayOff())
        );
        dayOff.setDayOff(entity.getDayOff());
        log.info("dayOff {}", dayOff);
        return dayOffRepository.saveDayOff(dayOff);
    }

    public List<EmployeeDaysOffTo> getAllEmployeesWithDaysOff(LocalDate startDate, LocalDate endDate) {
        // карта сотрудников со списком приема и увольнения
        Map<String, List<Worked>> allEmployeeWorked = workedRepository.getAllWorkersBetween(startDate, endDate);
        // карта сотрудников с картой выходных дней
        Map<String, Map<LocalDate, DayOffAndWorkedDto>> allEmployeeDayOffTo = dayOffRepository.getAllEmployeeWithDaysOffBetween(startDate, endDate);

        allEmployeeWorked.forEach((employeeId, workers) -> {
            if (!allEmployeeDayOffTo.containsKey(employeeId)) {
                Map<LocalDate, DayOffAndWorkedDto> dasOffTo = new ConcurrentHashMap<>();
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    dasOffTo.put(date, new DayOffAndWorkedDto(date));
                }
                allEmployeeDayOffTo.put(employeeId, dasOffTo);
            }
            Map<LocalDate, DayOffAndWorkedDto> datesDayOffTo = allEmployeeDayOffTo.get(employeeId);
            workers.forEach(worked -> {
                LocalDate startLocalDate = worked.getRecruitment().isBefore(startDate) ? startDate : worked.getRecruitment();
                LocalDate endLocalDate = Objects.requireNonNullElse(worked.getDismissal(), endDate).isAfter(endDate.minusDays(1)) ? endDate : worked.getDismissal();
                for (LocalDate date = startLocalDate; date.isBefore(endLocalDate); date = date.plusDays(1)) {
                    datesDayOffTo.get(date).setWorked(true);
                }
            });
        });

//        System.out.println(allEmployeeWorked);

        List<EmployeeDaysOffTo> employeeDaysOffToList = new ArrayList<>();
        List<Employee> employees = employeeRepository.getAll();
        employees.forEach(employee -> {
            if (allEmployeeWorked.containsKey(employee.getId())) {
                Worked last = allEmployeeWorked.get(employee.getId()).stream()
                        .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                        .findFirst().orElse(new Worked());
                EmployeeDto employeeDto = Util.getEmployeeDto(employee, last);
                List<DayOffAndWorkedDto> daysOff = allEmployeeDayOffTo.get(employee.getId()).values().stream()
                        .sorted(Comparator.comparing(DayOffAndWorkedDto::getDate)).collect(Collectors.toList());
                employeeDaysOffToList.add(new EmployeeDaysOffTo(employeeDto, daysOff));
            }
        });
        return employeeDaysOffToList;
    }
}
