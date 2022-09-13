package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.repository.ReportRepository;
import com.lvv.ttimpex2.to.ReportDataTo;
import com.lvv.ttimpex2.to.ReportTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
//@ApiIgnore
@RestController
@RequestMapping(value = "api/ts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ReportRepository repository;

    public ReportController(ReportRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ReportTo getAll() {
        log.info("getAll ReportTo");
        return repository.getReportTo();
    }

//    @GetMapping(value = "/rep", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<RepTo> getRep() {
//        log.info("getAll RepTo");
//        return List.of(new RepTo("Name", 35));
//    }

}
