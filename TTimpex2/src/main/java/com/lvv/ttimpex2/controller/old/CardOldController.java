package com.lvv.ttimpex2.controller.old;

import com.lvv.ttimpex2.molel.old.CardOld;
import com.lvv.ttimpex2.service.old.CardService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
//@RestController
//@RequestMapping("/rest/card")
public final class CardOldController {
    private CardService service;

    public CardOldController(CardService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<CardOld> findAll() {
        return service.findAll();
    }

    @GetMapping("/check")
    public List<CardOld> check() {
        service.checkCard();
        return findAll();
    }
}
