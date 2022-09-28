package com.lvv.ttimpex2.to;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Map<LocalDate, DayOffTo> getMapDaysOffTo() {
        return daysOffTo.stream()
                .collect(Collectors.toMap(DayOffTo::getDate, Function.identity()));
    }

    @Override
    public String toString() {
        return "EmployeeDaysOffTo{" +
                "employeeTo=" + employeeTo.getId() +
                ", dayOff=" + daysOffTo +
                '}';
    }
}
