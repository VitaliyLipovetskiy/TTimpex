package com.lvv.ttimpex2.to;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Vitalii Lypovetskyi
 */
public class DayTo {
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDate date;
//    private final Integer dayOfMonth;
//    private final String dayOfWeek; // день недели
    private final String cameAutoTime;
    private final String cameCorrectTime;
    private final String exitAutoTime;
    private final String exitCorrectTime;
    private final Boolean dayOff;// выходной
    private final Integer fine;  // штраф
    private final Double worked; // отработано в днях

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, 0);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Integer fine) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, fine, 0.0);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Double worked) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, 0, false, worked);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Boolean dayOff) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, dayOff, 0.0);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Boolean dayOff, Double worked) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, 0, dayOff, worked);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Integer fine, Double worked) {
        this(date, cameAutoTime, cameCorrectTime, exitAutoTime, exitCorrectTime, fine, false, worked);
    }

    public DayTo(LocalDate date, String cameAutoTime, String cameCorrectTime, String exitAutoTime, String exitCorrectTime, Integer fine, Boolean dayOff, Double worked) {
        this.date = date;
//        this.dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru-RU"));
//        this.dayOfMonth = date.getDayOfMonth();
        this.cameAutoTime = cameAutoTime;
        this.cameCorrectTime = cameCorrectTime;
        this.exitAutoTime = exitAutoTime;
        this.exitCorrectTime = exitCorrectTime;
        this.fine = fine;
        this.worked = worked;
        this.dayOff = dayOff;
    }

    public LocalDate getDate() {
        return date;
    }

//    public int getDayOfMonth() {
//        return dayOfMonth;
//    }

//    public String getDayOfWeek() {
//        return dayOfWeek;
//    }

    public String getCameAutoTime() {
        return cameAutoTime;
    }

    public String getCameCorrectTime() {
        return cameCorrectTime;
    }

    public String getExitAutoTime() {
        return exitAutoTime;
    }

    public String getExitCorrectTime() {
        return exitCorrectTime;
    }

    public Integer getFine() {
        return fine;
    }

    public Double getWorked() {
        return worked;
    }

    public Boolean getDayOff() {
        return dayOff;
    }

    @Override
    public String toString() {
        return "DayTo{" +
                "date=" + date +
//                ", dayOfMonth='" + dayOfMonth + '\'' +
//                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", cameAutoTime='" + cameAutoTime + '\'' +
                ", cameCorrectTime='" + cameCorrectTime + '\'' +
                ", exitAutoTime='" + exitAutoTime + '\'' +
                ", exitCorrectTime='" + exitCorrectTime + '\'' +
                ", fine=" + fine +
                ", worked=" + worked +
                '}';
    }
}
