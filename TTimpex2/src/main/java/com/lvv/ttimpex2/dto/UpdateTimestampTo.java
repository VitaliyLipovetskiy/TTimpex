package com.lvv.ttimpex2.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateTimestampTo {
    private String id;
    private UUID employeeId;
    private LocalDate date;
    private LocalTime comingCorrectTime;
    private LocalTime leavingCorrectTime;
    private Integer penalty;
    private Double workedOut;
}
