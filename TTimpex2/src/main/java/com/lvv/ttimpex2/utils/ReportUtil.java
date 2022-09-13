package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.to.EmployeeTo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class ReportUtil {

    public static final List<Department> departments = Arrays.asList(
            new Department(1, "Сервис 15"),
            new Department(2, "Сервис 38")
    );

    public static final List<Employee> employees = Arrays.asList(
            new Employee(1, "Иванов Иван", departments.get(0), "0012", true, LocalTime.of(10,15), LocalTime.of(18,30)),
            new Employee(2, "Сидоров Сидор", departments.get(0), null, true, null, null),
            new Employee(3, "Петров Петр", departments.get(0), "0035", true, null, null),
            new Employee(4, "Васечкин Вася", departments.get(1), "0067", false, LocalTime.of(9,45), LocalTime.of(18,0)),
            new Employee(5, "Андреев Андрей", departments.get(1), "0039"),
            new Employee(6, "Смирнов Сергей", departments.get(1), "0072"),
            new Employee(7, "Сергеев Антон", departments.get(1), "0006"),
            new Employee(8, "Сычев Дмитрий"),
            new Employee(9, "Шпаковский Виктор")
    );
}
