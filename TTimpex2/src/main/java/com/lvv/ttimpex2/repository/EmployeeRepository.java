package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {

    Optional<Employee> save(Employee employee);

    Optional<Employee> deleteById(UUID id);

    Optional<Employee> getById(UUID id);

    List<Employee> getAll();

    Optional<Employee> findFirstByFirstNameAndLastName(String firstName, String lastName);

}
