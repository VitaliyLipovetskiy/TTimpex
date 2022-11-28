package com.lvv.ttimpex2.dto;

import com.lvv.ttimpex2.molel.DayOff;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//@JsonDeserialize(using = DayOffDeserializer.class)
public class DayOffAndWorkedDto implements Serializable {

    private LocalDate date;
    private Boolean dayOff;
    private Boolean worked;

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }

    public String getDayOfWeek() {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU"));
    }

    public DayOffAndWorkedDto(LocalDate date, Boolean dayOff) {
        this(date, dayOff, false);
    }

    public DayOffAndWorkedDto(LocalDate date) {
        this(date,false);
    }

    public DayOffAndWorkedDto(DayOff dayOff) {
        this(dayOff.getEmployeeDate().getDate(), dayOff.getDayOff(), false);
    }
}
