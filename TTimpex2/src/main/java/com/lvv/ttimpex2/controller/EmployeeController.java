package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.WorkedRepository;
import com.lvv.ttimpex2.service.DayOffService;
import com.lvv.ttimpex2.service.EmployeeService;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EmployeeService employeeService;
    private final WorkedRepository workedRepository;
    private final DayOffService dayOffService;

    public EmployeeController(EmployeeService employeeService, WorkedRepository workedRepository, DayOffService dayOffService) {
        this.employeeService = employeeService;
        this.workedRepository = workedRepository;
        this.dayOffService = dayOffService;
    }

    @GetMapping
    public List<EmployeeTo> getAllEmployeeTo() {
        log.info("getAllEmployeeTo");
        return employeeService.getAllEmployed();
    }

    @GetMapping("/{id}")
    public EmployeeTo get(@PathVariable int id) {
        log.info("getById employee {}", id);
        return employeeService.get(id);
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int id) {
//        log.info("delete employee {}", id);
//        employeeRepository.worked(id);
//        employeeRepository.delete(id);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestBody EmployeeTo employeeTo) {
//        log.info(employeeTo.toString());
        Employee employee = new Employee(employeeTo);
        log.info("createOrUpdate " + employee);
        if (employee.isNew()) {
            log.info("create {}", employee);
//            checkNew(employee);
            Assert.notNull(employee, "employee must not be null");
            employeeService.save(employee);
        } else {
            log.info("update {}", employee);
//            assureIdConsistent(meal, id);
            Assert.notNull(employee, "employee must not be null");
//            checkNotFoundWithId(repository.save(meal, userId), meal.id());
            employeeService.save(employee);
        }
        employeeTo.setId(employee.getId());
        Worked worked = Util.createWorked(employeeTo);
        log.info("createOrUpdate Worked " + worked);
        workedRepository.save(worked);

    }

}
