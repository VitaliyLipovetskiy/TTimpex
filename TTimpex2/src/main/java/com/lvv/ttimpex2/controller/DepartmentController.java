package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.repository.DepartmentRepository;
import com.lvv.ttimpex2.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/department", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Department> getAll() {
        log.info("getAllEmployeeTo");
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Department get(@PathVariable int id) {
        log.info("getById employee {}", id);
        return repository.get(id);
    }

}
