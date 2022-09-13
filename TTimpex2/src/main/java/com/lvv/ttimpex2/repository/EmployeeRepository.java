package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface EmployeeRepository {

    Employee save(Employee employee);

    boolean delete(int id);

    boolean worked(int id);
    Employee get(int id);

    List<Employee> getAll();

    List<Employee> getAllWorked();
}
