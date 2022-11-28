package com.lvv.ttimpex2.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReportDataTo {
    private final String id;
    private final String name;
    private final Boolean accountingForHoursWorked;
    private final Integer penalty;  // штраф
    private final Double workedOut; // отработано в днях
    private final List<DayDto> daysDto;
}
