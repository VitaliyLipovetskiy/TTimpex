package com.lvv.ttimpex2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@AllArgsConstructor
@Getter
@ToString
public class DayDto {
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDate date;
    private final LocalTime comingAutoTime;
    private final LocalTime comingCorrectTime;
    private final LocalTime leavingAutoTime;
    private final LocalTime leavingCorrectTime;
    private final Integer penalty;      // штраф
    private final Boolean dayOff;       // выходной
    private final Boolean worked;       // не уволен
    private final Double workedOut;     // отработано в днях
//    private final Integer penaltyAuto;  // штраф рассчитанный

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0);
    }

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Integer penalty) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, penalty, 0.0);
    }

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0, true,false, workedOut);
    }

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Boolean dayOff, Boolean worked) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, dayOff, worked, 0.0);
    }

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Boolean dayOff, Boolean worked, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0, dayOff, worked, workedOut);
    }

    public DayDto(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Integer penalty, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, penalty, false, true, workedOut);
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }

//    @JsonIgnore
    public String getDayOfWeek() {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU"));
    }
}
