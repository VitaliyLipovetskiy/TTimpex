package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvv.ttimpex2.utils.json.DayOffDeserializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Vitalii Lypovetskyi
 */
@JsonDeserialize(using = DayOffDeserializer.class)
public class DayOff implements Serializable {
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
//    private Employee employee;
    private Boolean dayOff;
    private Boolean worked;

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

    public Boolean getWorked() {
        return worked;
    }

    public void setWorked(Boolean worked) {
        this.worked = worked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayOff dayOff1 = (DayOff) o;
        return date.equals(dayOff1.date) && dayOff.equals(dayOff1.dayOff) && worked.equals(dayOff1.worked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, dayOff, worked);
    }

    @Override
    public String toString() {
        return "DayOff{" +
                "date=" + date +
                ", dayOff=" + dayOff +
                ", worked=" + worked +
                '}';
    }
}
