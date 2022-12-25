package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaEmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findFirstByFirstNameAndLastName(String firstName, String lastName);
}
