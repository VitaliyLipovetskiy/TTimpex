package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.service.TimeStampService;
import com.lvv.ttimpex2.to.ColumnTo;
import com.lvv.ttimpex2.to.ReportDataTo;
import com.lvv.ttimpex2.to.ReportTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = "api/ts", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeStampController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TimeStampService timeStampService;

    public TimeStampController(TimeStampService timeStampService) {
        this.timeStampService = timeStampService;
    }

//    @GetMapping
    public Collection[] getFiltered(@RequestParam @Nullable Map<String, LocalDate> param) {
        log.info("getFiltered {}", param);
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        LocalDate endDate = LocalDate.of(today.getYear(), today.plusMonths(1).getMonthValue(), 1);
        LocalDate localDate;
        if (param != null) {
            localDate = param.get("date");
            if (localDate == null) {
                startDate = param.getOrDefault("startDate", startDate);
                endDate = param.getOrDefault("endDate", endDate);
            } else {
                startDate = localDate;
                endDate = localDate;
            }
        }
        List<ColumnTo> columnTos = new ArrayList<>();
        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));

        return new Collection[] {columnTos, timeStampService.getFilteredForReport(startDate, endDate)};
    }

    @GetMapping
    public ReportTo getFiltered1(@RequestParam @Nullable Map<String, LocalDate> param) {
        log.info("getFiltered {}", param);
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        LocalDate endDate = LocalDate.of(today.getYear(), today.plusMonths(1).getMonthValue(), 1);
        LocalDate localDate;
        if (param != null) {
            localDate = param.get("date");
            if (localDate == null) {
                startDate = param.getOrDefault("startDate", startDate);
                endDate = param.getOrDefault("endDate", endDate);
            } else {
                startDate = localDate;
                endDate = localDate;
            }
        }
        List<ColumnTo> columnTos = new ArrayList<>();
        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));

        return new ReportTo(timeStampService.getFilteredForReport(startDate, endDate), columnTos);
    }
}
