package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.service.SCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping("/rest/scode")
public class SCodeController {
    private SCodeService service;

    public SCodeController(SCodeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<SCode> findAll() {
        return service.findAll();
    }

    @GetMapping("/check")
    public List<SCode> check() {
        service.checkSCode();
        return findAll();
    }
}
