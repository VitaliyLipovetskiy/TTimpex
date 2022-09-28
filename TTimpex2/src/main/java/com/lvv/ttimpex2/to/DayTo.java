package com.lvv.ttimpex2.to;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
public class DayTo {
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDate date;
//    private final Integer dayOfMonth;
//    private final String dayOfWeek; // день недели
    private final LocalTime comingAutoTime;
    private final LocalTime comingCorrectTime;
    private final LocalTime leavingAutoTime;
    private final LocalTime leavingCorrectTime;
    private final Boolean dayOff;   // выходной
    private final Boolean worked;   // не уволен
    private final Integer penalty;  // штраф
    private final Double workedOut; // отработано в днях

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Integer penalty) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, penalty, 0.0);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0, true,false, workedOut);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Boolean dayOff, Boolean worked) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, dayOff, worked, 0.0);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Boolean dayOff, Boolean worked, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, 0, dayOff, worked, workedOut);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Integer penalty, Double workedOut) {
        this(date, comingAutoTime, comingCorrectTime, leavingAutoTime, leavingCorrectTime, penalty, false, true, workedOut);
    }

    public DayTo(LocalDate date, LocalTime comingAutoTime, LocalTime comingCorrectTime, LocalTime leavingAutoTime, LocalTime leavingCorrectTime, Integer penalty, Boolean dayOff, Boolean worked, Double workedOut) {
        this.date = date;
        this.comingAutoTime = comingAutoTime;
        this.comingCorrectTime = comingCorrectTime;
        this.leavingAutoTime = leavingAutoTime;
        this.leavingCorrectTime = leavingCorrectTime;
        this.penalty = penalty;
        this.workedOut = workedOut;
        this.dayOff = dayOff;
        this.worked = worked;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getComingAutoTime() {
        return comingAutoTime;
    }

    public LocalTime getComingCorrectTime() {
        return comingCorrectTime;
    }

    public LocalTime getLeavingAutoTime() {
        return leavingAutoTime;
    }

    public LocalTime getLeavingCorrectTime() {
        return leavingCorrectTime;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public Double getWorkedOut() {
        return workedOut;
    }

    public Boolean getDayOff() {
        return dayOff;
    }

    public Boolean getWorked() {
        return worked;
    }

    @Override
    public String toString() {
        return "DayTo{" +
                "date=" + date +
                ", comingAutoTime='" + comingAutoTime + '\'' +
                ", comingCorrectTime='" + comingCorrectTime + '\'' +
                ", leavingAutoTime='" + leavingAutoTime + '\'' +
                ", leavingCorrectTime='" + leavingCorrectTime + '\'' +
                ", penalty=" + penalty +
                ", workedOut=" + workedOut +
                ", worked=" + worked +
                ", dayOff=" + dayOff +
                '}';
    }
}
