package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.WorkedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "/api/worked", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkedController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkedRepository workedRepository;

    public WorkedController(WorkedRepository workedRepository) {
        this.workedRepository = workedRepository;
    }

    @GetMapping("/{id}")
    public Worked getLast(@PathVariable int id) {
        log.info("getById employee {}", id);
        return workedRepository.getLast(id);
    }

}
