package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.*;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.to.TimeStampTo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Vitalii Lypovetskyi
 */
public class Util {
    private Util() {}

    public static List<TimeStampTo> getTos(Collection<TimeStamp> timeStamps) {
        return null;
    }

    private static List<TimeStampTo> filterByPredicate(Collection<TimeStamp> timeStamps, Card card, SCode sCode, Predicate<TimeStamp> filter) {
        return null;
    }

    public static EmployeeTo getEmployeeTo(Employee employee, Worked worked) {
        EmployeeTo employeeTo = createEmployeeTo(employee);
        employeeTo.setRecruitment(worked.getRecruitment());
        employeeTo.setDismissal(worked.getDismissal());
        return employeeTo;
    }

    public static List<EmployeeTo> getEmployeeTos(Collection<Employee> employees, Map<Integer, Worked> workedMap) {
        return filterByPredicate(employees, workedMap, employeeTo -> true);
    }

    public static List<EmployeeTo> getFilteredEmployeeTos(Collection<Employee> employees, Map<Integer, Worked> workedMap) {
        return filterByPredicate(employees, workedMap, employeeTo -> employeeTo.getRecruitment() != null);
    }

    public static Employee createEmployee(EmployeeTo empTo) {
        return new Employee(empTo.getId(), empTo.getFirstName(), empTo.getLastName(), empTo.getMiddleName(),
                empTo.getDepartment(), empTo.getCardId(), empTo.getStartTime(), empTo.getEndTime());
    }

    public static Worked createWorked(EmployeeTo empTo) {
        return new Worked(createEmployee(empTo), empTo.getRecruitment(), empTo.getDismissal());
    }

    private static List<EmployeeTo> filterByPredicate(Collection<Employee> employees, Map<Integer, Worked> workedMap, Predicate<EmployeeTo> filter) {
        List<EmployeeTo> employeeTos = employees.stream()
                .map(Util::createEmployeeTo).toList();
        employeeTos.forEach(employeeTo -> {
            Worked worked = workedMap.get(employeeTo.getId());
            if (worked != null) {
                employeeTo.setDismissal(worked.getDismissal());
                employeeTo.setRecruitment(worked.getRecruitment());
            }
        });
        return employeeTos.stream().filter(filter).toList();
    }

    private static EmployeeTo createEmployeeTo(Employee emp) {
        return new EmployeeTo(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getMiddleName(), emp.getDepartment(), emp.getCardId(),
                null, null, emp.getStartTime(), emp.getEndTime());
    }

    private static TimeStampTo createTo(TimeStamp timeStamp, Card card, SCode sCode) {
        return new TimeStampTo(timeStamp.getId(), timeStamp.getDateTime(), timeStamp.getPost(), timeStamp.getEvent(), card, sCode);
    }

}
