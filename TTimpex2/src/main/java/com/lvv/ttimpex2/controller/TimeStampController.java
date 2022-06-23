package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.service.TimeStampService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping("/rest")
public class TimeStampController {

    private TimeStampService service;

    public TimeStampController(TimeStampService service) {
        this.service = service;
    }

    @GetMapping("/all/ww")
    public List<TimeStamp> findAll1(@RequestParam Map<String,String> params) {
        Sort order = Sort.by(Sort.Direction.DESC, "dateTime");
        Map<String, String> filter = new HashMap<>();
        filter.put("date", LocalDate.now().minusDays(1).toString());
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "100"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));
        return service.findAll(PageRequest.of(pageNumber, pageSize, order), filter).toList();
    }

    @GetMapping("/all")
    public List<TimeStamp> findAll(@RequestParam Map<String,String> params) {//, @PathVariable("date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        Sort order = Sort.by(Sort.Direction.DESC, "dateTime");
        String localDate = params.getOrDefault("date", LocalDate.now().minusDays(1).toString());
        Map<String, String> filter = new HashMap<>();
        filter.put("date", localDate);
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "100"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));
        return service.findAll(PageRequest.of(pageNumber, pageSize, order), filter).toList();
    }

    @GetMapping("/last_card/{card}")
    public TimeStamp getTopByCard(@PathVariable("card") String card) {
        return service.getFirstByCard(card);
    }
}
