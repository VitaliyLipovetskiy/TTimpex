package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.service.TimeStampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping("/rest")
public class TimeStampController {

    private TimeStampService service;

    @Autowired
    public TimeStampController(TimeStampService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<TimeStamp> findAll() {
        return service.findAll();
    }

    @GetMapping("/last_card/{card}")
    public TimeStamp getTopByCard(@PathVariable("card") String card) {
        return service.getFirstByCard(card);
    }
}
