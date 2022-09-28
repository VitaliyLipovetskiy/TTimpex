package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.*;
import com.lvv.ttimpex2.molel.old.CardOld;
import com.lvv.ttimpex2.molel.old.SCodeOld;
import com.lvv.ttimpex2.molel.old.TimeStampOld;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.to.old.TimeStampOldTo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Vitalii Lypovetskyi
 */
public class Util {
    private Util() {}

    public static List<TimeStampOldTo> getTos(Collection<TimeStampOld> timeStampsOld) {
        return null;
    }

    private static List<TimeStampOldTo> filterByPredicate(Collection<TimeStampOld> timeStampsOld, CardOld cardOld, SCodeOld sCodeOld, Predicate<TimeStampOld> filter) {
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

    public static Worked createWorked(EmployeeTo empTo) {
        return new Worked(new Employee(empTo), empTo.getRecruitment(), empTo.getDismissal());
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
                null, null, emp.getStartTime(), emp.getEndTime(), emp.getAccountingForHoursWorked());
    }

    private static TimeStampOldTo createTo(TimeStampOld timeStampOld, CardOld cardOld, SCodeOld sCodeOld) {
        return new TimeStampOldTo(timeStampOld.getId(), timeStampOld.getDateTime(), timeStampOld.getPost(), timeStampOld.getEvent(), cardOld, sCodeOld);
    }

}
