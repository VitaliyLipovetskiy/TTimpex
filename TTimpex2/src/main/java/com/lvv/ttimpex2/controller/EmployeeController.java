package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<EmployeeTo> getAllEmployeeTo() {
        log.info("getAllEmployeeTo");
        return Util.getEmployeeTos(employeeRepository.getAll());
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable int id) {
        log.info("getById employee {}", id);
        return employeeRepository.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete employee {}", id);
        employeeRepository.worked(id);
//        employeeRepository.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestBody Employee employee) {
        log.info("createOrUpdate " + employee.toString());
        if (employee.isNew()) {
            log.info("create {}", employee);
//            checkNew(employee);
            Assert.notNull(employee, "employee must not be null");
            employeeRepository.save(employee);
        } else {
            log.info("update {}", employee);
//            assureIdConsistent(meal, id);
            Assert.notNull(employee, "employee must not be null");
//            checkNotFoundWithId(repository.save(meal, userId), meal.id());
            employeeRepository.save(employee);
        }
    }

}
