package com.lvv.ttimpex2.controller.old;

import com.lvv.ttimpex2.molel.old.SCodeOld;
import com.lvv.ttimpex2.service.old.SCodeService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
//@RestController
//@RequestMapping("/rest/scode")
public final class SCodeOldController {
    private SCodeService service;

    public SCodeOldController(SCodeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<SCodeOld> findAll() {
        return service.findAll();
    }

    @GetMapping("/check")
    public List<SCodeOld> check() {
        service.checkSCode();
        return findAll();
    }
}
