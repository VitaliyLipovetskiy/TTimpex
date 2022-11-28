package com.lvv.ttimpex2.dto.old;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


public class ColumnTo {
    private final int dayOfMonth;   // день месяца
    private final String dayOfWeek; // день недели

    public ColumnTo(LocalDate date) {
        this.dayOfMonth = date.getDayOfMonth();
        this.dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU"));
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
