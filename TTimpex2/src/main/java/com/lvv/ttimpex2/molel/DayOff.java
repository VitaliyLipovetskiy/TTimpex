package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvv.ttimpex2.utils.json.DayOffDeserializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Vitalii Lypovetskyi
 */
@JsonDeserialize(using = DayOffDeserializer.class)
public class DayOff implements Serializable {
    private Employee employee;
    private LocalDate date;
    private Boolean dayOff;

    public DayOff() {}

    public DayOff(Employee employee, LocalDate date, Boolean dayOff) {
        this.employee = employee;
        this.date = date;
        this.dayOff = dayOff;
    }

    public DayOff(LocalDate date) {
        this.date = date;
        this.dayOff = false;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean getDayOff() {
        return dayOff;
    }

    public void setDayOff(Boolean dayOff) {
        this.dayOff = dayOff;
    }

    @Override
    public String toString() {
        return "DayOff{" +
                "employee=" + employee +
                ", date=" + date +
                ", dayOff=" + dayOff +
                '}';
    }
}
