package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping("/rest/card")
public class CardController {
    private CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Card> findAll() {
        return service.findAll();
    }

    @GetMapping("/check")
    public List<Card> check() {
        service.checkCard();
        return findAll();
    }
}
