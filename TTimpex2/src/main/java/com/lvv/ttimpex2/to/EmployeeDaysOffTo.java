package com.lvv.ttimpex2.to;

import com.lvv.ttimpex2.molel.DayOff;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class EmployeeDaysOffTo {
    private final EmployeeTo employeeTo;
    private final List<DayOff> dayOff;

    public EmployeeDaysOffTo(EmployeeTo employeeTo, List<DayOff> dayOff) {
        this.employeeTo = employeeTo;
        this.dayOff = dayOff;
    }

    public EmployeeTo getEmployeeTo() {
        return employeeTo;
    }

    public List<DayOff> getDayOff() {
        return dayOff;
    }

}
