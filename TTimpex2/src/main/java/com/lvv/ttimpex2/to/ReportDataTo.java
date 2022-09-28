package com.lvv.ttimpex2.to;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class ReportDataTo {
    private final int id;
    private final String name;
    private final Boolean accountingForHoursWorked;
    private final Integer penalty;  // штраф
    private final Double workedOut; // отработано в днях
    private final List<DayTo> daysTo;

    public ReportDataTo(int id, String name, Boolean accountingForHoursWorked, Integer penalty, Double workedOut, List<DayTo> daysTo) {
        this.id = id;
        this.name = name;
        this.accountingForHoursWorked = accountingForHoursWorked;
        this.penalty = penalty;
        this.workedOut = workedOut;
        this.daysTo = daysTo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DayTo> getDaysTo() {
        return daysTo;
    }

    public Boolean getAccountingForHoursWorked() {
        return accountingForHoursWorked;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public Double getWorkedOut() {
        return workedOut;
    }

    @Override
    public String toString() {
        return "ReportTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", daysTo=" + daysTo +
                '}';
    }
}
