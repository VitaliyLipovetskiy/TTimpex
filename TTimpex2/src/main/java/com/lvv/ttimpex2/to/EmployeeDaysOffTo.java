package com.lvv.ttimpex2.to;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class EmployeeDaysOffTo {
    private final EmployeeTo employeeTo;
    private final List<DayOffTo> daysOffTo;

    public EmployeeDaysOffTo(EmployeeTo employeeTo, List<DayOffTo> daysOff) {
        this.employeeTo = employeeTo;
        this.daysOffTo = daysOff;
    }

    public EmployeeTo getEmployeeTo() {
        return employeeTo;
    }

    public List<DayOffTo> getDaysOffTo() {
        return daysOffTo;
    }

    @Override
    public String toString() {
        return "EmployeeDaysOffTo{" +
                "employeeTo=" + employeeTo.getId() +
                ", dayOff=" + daysOffTo +
                '}';
    }
}
