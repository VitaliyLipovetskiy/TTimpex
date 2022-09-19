package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface EmployeeRepository {

    Employee save(Employee employee);

    boolean delete(int id);

    Employee get(int id);

    List<Employee> getAll();
}
