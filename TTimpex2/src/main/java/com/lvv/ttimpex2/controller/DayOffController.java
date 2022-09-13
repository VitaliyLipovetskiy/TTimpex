package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.to.ColumnTo;
import com.lvv.ttimpex2.to.EmployeeDaysOffTo;
import com.lvv.ttimpex2.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/employees/daysoff", produces = MediaType.APPLICATION_JSON_VALUE)
public class DayOffController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DaysOffRepository daysOffRepository;
    private final EmployeeRepository employeeRepository;

    public DayOffController(DaysOffRepository daysOffRepository, EmployeeRepository employeeRepository) {
        this.daysOffRepository = daysOffRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public Collection[] getBetweenDaysOffTo() {
        log.info("getBetweenDaysOffTo");
        LocalDate startDate = LocalDate.of(2022, 7, 1);
        LocalDate endDate = LocalDate.of(2022, 7, 31);

        Collection<EmployeeDaysOffTo> employeeDaysOffTo = new ArrayList<>();
        Util.getEmployeeTos(employeeRepository.getAllWorked())
                .forEach(employeeTo ->
                    employeeDaysOffTo.add(new EmployeeDaysOffTo(employeeTo,
                            daysOffRepository.getBetween(startDate, endDate, employeeTo.getId()))));

        Collection<ColumnTo> columnTos = new ArrayList<>();
        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));

        return new Collection[] {columnTos, employeeDaysOffTo};
    }
}
