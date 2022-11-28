package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@AllArgsConstructor
@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JpaEmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> save(Employee employee) {
        try {
            return Optional.of(employeeRepository.save(employee));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> deleteById(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(String.valueOf(id));
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
//            employeeRepository.flush();
            return optionalEmployee;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> getById(UUID id) {
        return employeeRepository.findById(String.valueOf(id));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findFirstByFirstNameAndLastName(String firstName, String lastName) {
        log.info("Find first employee by {} {}", firstName, lastName);
        Optional<Employee> employee = employeeRepository.findFirstByFirstNameAndLastName(firstName, lastName);
        log.info("Find first employee {}", employee);
        return employee;
    }
}
