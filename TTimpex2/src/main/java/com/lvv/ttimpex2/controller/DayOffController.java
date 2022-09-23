package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.service.DayOffService;
import com.lvv.ttimpex2.service.EmployeeService;
import com.lvv.ttimpex2.to.ColumnTo;
import com.lvv.ttimpex2.to.EmployeeDaysOffTo;
import com.lvv.ttimpex2.to.EmployeeTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/employees/daysoff", produces = MediaType.APPLICATION_JSON_VALUE)
public class DayOffController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DayOffService dayOffService;
    private final EmployeeService employeeService;

    public DayOffController(DayOffService dayOffService, EmployeeService employeeService) {
        this.dayOffService = dayOffService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public Collection[] getBetweenDaysOffTo(@RequestParam @Nullable String filterMonth) {
        log.info("getBetweenDaysOffTo {}", filterMonth);
        String[] parts = new String[0];
        if (filterMonth != null) {
            parts = filterMonth.split("-");
        }
        int year, month;
        try {
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

//        Collection<EmployeeDaysOffTo> employeeDaysOffToList = new ArrayList<>();
//        employeeService.getAllEmployedForThePeriod(startDate, endDate)
//
//                .forEach(employeeTo -> {
//                    List<DayOff> daysOffBetween = daysOffRepository.getBetween(startDate, endDate, employeeTo.getId());
//                    System.out.println("" + employeeTo.getId() + " " + daysOffBetween);
//                    EmployeeDaysOffTo employeeDaysOffTo = new EmployeeDaysOffTo(employeeTo, daysOffBetween);
//                    employeeDaysOffToList.add(employeeDaysOffTo);
//                    System.out.println(employeeDaysOffTo);
//                });
//
//        System.out.println("======");
//        employeeDaysOffToList.forEach(System.out::println);

        Collection<EmployeeDaysOffTo> employeeDaysOffToList = dayOffService.getAllEmployeesForWithHolidays(startDate, endDate);

        Collection<ColumnTo> columnTos = new ArrayList<>();
        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));

        return new Collection[] {columnTos, employeeDaysOffToList};
    }

    @PostMapping(value = "/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int employeeId, @RequestBody DayOff dayOff) {
        log.info("update {} {}", employeeId, dayOff);
        dayOffService.update(employeeId, dayOff);
    }
}
