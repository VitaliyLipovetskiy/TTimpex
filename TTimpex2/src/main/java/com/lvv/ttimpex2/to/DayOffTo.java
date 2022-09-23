package com.lvv.ttimpex2.to;

import com.lvv.ttimpex2.molel.DayOff;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Vitalii Lypovetskyi
 */
//@JsonDeserialize(using = DayOffDeserializer.class)
public class DayOffTo implements Serializable {

    private LocalDate date;
    //    private Employee employee;
    private Boolean dayOff;
    private Boolean worked;

    public DayOffTo() {}

    public DayOffTo(LocalDate date, Boolean dayOff, Boolean worked) {
        this.date = date;
        this.dayOff = dayOff;
        this.worked = worked;
    }

    public DayOffTo(LocalDate date, Boolean dayOff) {
        this(date, dayOff, false);
    }

    public DayOffTo(LocalDate date) {
        this(date,false);
    }

    public DayOffTo(DayOff dayOff) {
        this(dayOff.getDate(), dayOff.getDayOff(), false);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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
    public String toString() {
        return "DayOfTo{" +
                "date=" + date +
                ", dayOff=" + dayOff +
                ", worked=" + worked +
                '}';
    }
}
