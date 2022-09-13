package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.to.TimeStampTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Vitalii Lypovetskyi
 */
public class Util {
    private Util() {}

    public static List<TimeStampTo> getTos(Collection<TimeStamp> timeStamps) {
        return null;
    }

    public static List<TimeStampTo> filterByPredicate(Collection<TimeStamp> timeStamps, Card card, SCode sCode, Predicate<TimeStamp> filter) {
        return null;
    }

    private static TimeStampTo createTo(TimeStamp timeStamp, Card card, SCode sCode) {
        return new TimeStampTo(timeStamp.getId(), timeStamp.getDateTime(), timeStamp.getPost(), timeStamp.getEvent(), card, sCode);
    }

    private static EmployeeTo createEmployeeTo(Employee emp) {
        return new EmployeeTo(emp.getId(), emp.getNames(), emp.getNameDepartment(), emp.getCardId(),
                emp.getWorked(), emp.getStartTime(), emp.getEndTime());
    }

    public static List<EmployeeTo> getEmployeeTos(Collection<Employee> employees) {
        return employees.stream()
                .map(Util::createEmployeeTo)
                .collect(Collectors.toList());
    }
}
