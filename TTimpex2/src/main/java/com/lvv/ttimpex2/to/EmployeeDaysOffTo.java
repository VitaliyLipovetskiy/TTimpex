package com.lvv.ttimpex2.to;

import com.lvv.ttimpex2.molel.DayOff;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Vitalii Lypovetskyi
 */
public class EmployeeDaysOffTo {
    private final EmployeeTo employeeTo;
    private final List<DayOff> daysOff;

    public EmployeeDaysOffTo(EmployeeTo employeeTo, List<DayOff> daysOff) {
        this.employeeTo = employeeTo;
        this.daysOff = daysOff;
    }

    public EmployeeTo getEmployeeTo() {
        return employeeTo;
    }

    public List<DayOff> getDaysOff() {
        return daysOff;
    }

    @Override
    public String toString() {
        return "EmployeeDaysOffTo{" +
                "employeeTo=" + employeeTo.getId() +
                ", dayOff=" + daysOff +
                '}';
    }
}
