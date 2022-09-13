package com.lvv.ttimpex2.molel;

import java.time.LocalDate;

/**
 * @author Vitalii Lypovetskyi
 */
public class DayOff {

    private LocalDate date;
//    private Employee employee;
    private Boolean dayOff;

    public DayOff() {}

    public DayOff(LocalDate date, Boolean dayOff) {
        this.date = date;
        this.dayOff = dayOff;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    public Employee getEmployee() {
//        return employee;
//    }

//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }

    public Boolean getDayOff() {
        return dayOff;
    }

    public void setDayOff(Boolean dayOff) {
        this.dayOff = dayOff;
    }
}
