package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Timestamp;
import com.lvv.ttimpex2.service.TimestampService;
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
public class TimestampController {

    private TimestampService service;

    @Autowired
    public TimestampController(TimestampService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Timestamp> findAll() {
        return service.findAll();
    }

    @GetMapping("/last_card/{card}")
    public Timestamp getTopByCard(@PathVariable("card") String card) {
        return service.getFirstByCard(card);
    }
}
