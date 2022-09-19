package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.utils.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final WorkedRepository workedRepository;

    public EmployeeService(EmployeeRepository employeeRepository, WorkedRepository workedRepository) {
        this.employeeRepository = employeeRepository;
        this.workedRepository = workedRepository;
    }

    public List<EmployeeTo> getAllEmployedForThePeriod(LocalDate startDate, LocalDate endDate) {
        return Util.getFilteredEmployeeTos(employeeRepository.getAll(),
                workedRepository.getAllEmployedForThePeriod(startDate, endDate));
    }



    public List<EmployeeTo> getAllEmployed() {
        return Util.getEmployeeTos(employeeRepository.getAll(),
                workedRepository.getAllEmployed());
    }

    public EmployeeTo get(int id) {
        return Util.getEmployeeTo(employeeRepository.get(id), workedRepository.getLast(id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
