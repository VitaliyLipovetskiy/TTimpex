package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.DayDto;
import com.lvv.ttimpex2.dto.ReportDataTo;
import com.lvv.ttimpex2.service.TimeStampService;
import com.lvv.ttimpex2.dto.old.ReportTo;
import com.lvv.ttimpex2.dto.UpdateTimestampTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "api/ts", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeStampController {
    private final TimeStampService timeStampService;

//    @GetMapping
//    public Collection[] getFiltered(@RequestParam @Nullable Map<String, LocalDate> param) {
//        log.info("getFiltered {}", param);
//        LocalDate today = LocalDate.now();
//        LocalDate startDate = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
//        LocalDate endDate = LocalDate.of(today.getYear(), today.plusMonths(1).getMonthValue(), 1).minusDays(1);
//        LocalDate localDate;
//        if (param != null) {
//            localDate = param.getOrDefault("date", null);
//            if (localDate == null) {
//                startDate = param.getOrDefault("startDate", startDate);
//                endDate = param.getOrDefault("endDate", endDate);
//            } else {
//                startDate = localDate;
//                endDate = localDate;
//            }
//        }
//        List<ColumnTo> columnTos = new ArrayList<>();
//        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));
//
//         return new Collection[] {columnTos, timeStampService.getFilteredForReport(startDate, endDate.plusDays(1))};
//    }

    @GetMapping
    public ResponseEntity<?> getFiltered(
            @RequestParam @Nullable Map<String, LocalDate> param) {
        log.info("getFiltered {}", param);
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
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
        List<ReportDataTo> timeStampForReport = timeStampService.getFilteredForReport(startDate, endDate.plusDays(1));

        StringBuilder tableHeader = new StringBuilder();
        int months = Period.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(endDate.lengthOfMonth()).plusDays(1)).getMonths();
        for (int i = 0; i < months; i++) {
            tableHeader.append(Month.from(startDate.plusMonths(i)).getDisplayName(TextStyle.FULL_STANDALONE, new Locale("RU"))).append(" ");
        }
        List<String> columns;
        if (timeStampForReport.isEmpty()) {
//            startDate.datesUntil(endDate.plusDays(1)).forEach(date -> columns.add(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU"))));
            columns = startDate.datesUntil(endDate.plusDays(1))
                    .map(date -> date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU")))
                    .collect(Collectors.toList());
        } else {
            columns = timeStampForReport.get(0).getDaysDto().stream().map(DayDto::getDayOfWeek)
                    .collect(Collectors.toList());
        }
        long count52 = columns.stream().filter(column -> !"сбвс".contains(column)).count();
        tableHeader.append("(5/2 - ").append(count52);
        long count61 = columns.stream().filter(column -> !"вс".contains(column)).count();
        tableHeader.append(", 6/1 - ").append(count61).append(")");
        return ResponseEntity.ok(new ReportTo(timeStampForReport, tableHeader.toString()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTimestamp(@RequestBody UpdateTimestampTo updateTimestampTo) {
        log.info("updateTimestamp {}", updateTimestampTo);
        timeStampService.update(updateTimestampTo);
    }
}
