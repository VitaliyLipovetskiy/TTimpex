package com.lvv.ttimpex2.to;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class ReportDataTo {
    private final int id;
    private final String name;
    private final Boolean choice;
    private final Boolean accountingForHoursWorked;
    private final Integer fine;  // штраф
    private final Double worked; // отработано в днях
    private final List<DayTo> dayTos;

    public ReportDataTo(int id, String name, Boolean choice, Boolean accountingForHoursWorked, Integer fine, Double worked, List<DayTo> dayTos) {
        this.id = id;
        this.name = name;
        this.choice = choice;
        this.accountingForHoursWorked = accountingForHoursWorked;
        this.fine = fine;
        this.worked = worked;
        this.dayTos = dayTos;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DayTo> getDayTos() {
        return dayTos;
    }

    public Boolean getAccountingForHoursWorked() {
        return accountingForHoursWorked;
    }

    public Boolean getChoice() {
        return choice;
    }

    public Integer getFine() {
        return fine;
    }

    public Double getWorked() {
        return worked;
    }

    @Override
    public String toString() {
        return "ReportTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayTos=" + dayTos +
                '}';
    }
}
