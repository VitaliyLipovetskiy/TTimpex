package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    private LocalDate date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime; // время начала раб дня по умолчанию
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime; // время окончания раб дня по умолчанию
    private Integer penaltyBeingLate; // штраф за опоздание за 1 час
    private Integer penaltyEarlyCare; // штраф за ранний уход за 1 час
    private Integer penaltyForMissingTimestamp; // штраф за отсутствие отметки
    private Integer penaltyAbsenteeism; // штраф за прогул
}
