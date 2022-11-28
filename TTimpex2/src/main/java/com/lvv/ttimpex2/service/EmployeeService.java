package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.dto.UpdateEmployeeDto;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.dto.EmployeeDto;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;
import com.lvv.ttimpex2.utils.Util;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmployeeService implements ParadoxHandler {
    private final EmployeeRepository employeeRepository;
    private final WorkedService workedService;
    private final DepartmentService departmentService;
    private final SCodeService sCodeService;

    public List<EmployeeDto> getAllEmployedForThePeriod(LocalDate startDate, LocalDate endDate) {
        return Util.getFilteredEmployeeTos(employeeRepository.getAll(),
                workedService.getFirstWorkedForThePeriod(startDate, endDate));
    }

    public List<EmployeeDto> getAllEmployed() {
        return Util.getEmployeesDto(employeeRepository.getAll(),
                workedService.getAllWorkers());
    }

    public Employee getEmployeeById(UUID id) {
        return employeeRepository.getById(id).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find employee")
        );
    }

    public EmployeeDto getEmployeeDtoById(UUID id) {
        Employee employee = getEmployeeById(id);
        Worked worked = workedService.getLastWorkedByEmployeeId(String.valueOf(id));
        if (worked.getDismissal() != null && worked.getRecruitment() != null) {
            worked = null;
        }
        return Util.getEmployeeDto(employee, worked);
    }

//    @Transactional
    public Employee saveEmployee(Employee employee) {
        log.info("Save employee {}", employee);
        return employeeRepository.save(employee).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to save employee")
        );
    }

    @Transactional
    public Employee createEmployee(UpdateEmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setMiddleName(employeeDto.getMiddleName());
        if (employeeDto.getDepartmentId() != null) employee.setDepartment(departmentService.getDepartmentById(employeeDto.getDepartmentId()));
//        employee.setCardId(employeeDto.getCardId());
        employee.setStartTime(employeeDto.getStartTime());
        employee.setEndTime(employeeDto.getEndTime());
        employee.setAccountingForHoursWorked(employeeDto.getAccountingForHoursWorked());
        return saveEmployee(employee);
    }

    @Transactional
    public Employee updateEmployeeById(UUID employeeId, UpdateEmployeeDto employeeDto) {
        Employee employee = getEmployeeById(employeeId);
        if (employeeDto.getFirstName() != null && !Objects.equals(employeeDto.getFirstName(), employee.getFirstName())) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null && !Objects.equals(employeeDto.getLastName(), employee.getLastName())) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getMiddleName() != null && !Objects.equals(employeeDto.getMiddleName(), employee.getMiddleName())) {
            employee.setMiddleName(employeeDto.getMiddleName());
        }
        if (employee.getDepartment() == null) {
            if (employeeDto.getDepartmentId() != null) {
                employee.setDepartment(departmentService.getDepartmentById(employeeDto.getDepartmentId()));
            }
        } else {
            if (employeeDto.getDepartmentId() == null) {
                employee.setDepartment(null);
            } else if (!Objects.equals(employeeDto.getDepartmentId().toString(), employee.getDepartment().getId())) {
                employee.setDepartment(departmentService.getDepartmentById(employeeDto.getDepartmentId()));
            }
        }
//        if (employeeDto.getCardId() != null && !Objects.equals(employeeDto.getCardId(), employee.getCardId())) {
//            employee.setCardId(employeeDto.getCardId());
//        }
        if (employeeDto.getStartTime() != null && !Objects.equals(employeeDto.getStartTime(), employee.getStartTime())) {
            employee.setStartTime(employeeDto.getStartTime());
        }
        if (employeeDto.getEndTime() != null && !Objects.equals(employeeDto.getEndTime(), employee.getEndTime())) {
            employee.setEndTime(employeeDto.getEndTime());
        }
        if (employeeDto.getAccountingForHoursWorked() != null && !Objects.equals(employeeDto.getAccountingForHoursWorked(), employee.getAccountingForHoursWorked())) {
            employee.setAccountingForHoursWorked(employeeDto.getAccountingForHoursWorked());
        }
        return saveEmployee(employee);
    }

    @Override
    @Transactional
    public void call(Path pathDB, ResultSet resultSet, LocalDate date) throws SQLException {
        log.info("EmployeeService call");
        while (resultSet.next()) {
            log.info("{} {}", resultSet.getString("F"), resultSet.getString("I"));
            Optional<Employee> optionalEmployee = employeeRepository.findFirstByFirstNameAndLastName(
                    resultSet.getString("I"),
                    resultSet.getString("F")
            );
            Employee employee = optionalEmployee.orElseGet(() -> {
                Employee newEmployee = null;
                try {
                    newEmployee = new Employee(null,
                            resultSet.getString("I").trim(),
                            resultSet.getString("F").trim(),
                            "null".equals(resultSet.getString("O")) ? null : resultSet.getString("O").trim(),
                            null, null, null, false, null
                    );
                } catch (SQLException e) {
//                            throw new RuntimeException(e);
                }
                if (newEmployee != null) {
                    return saveEmployee(newEmployee);
                }
                return null;
            });
//            log.info("Employee {}", employee);
            SCode sCode = sCodeService.findById(resultSet.getString("CARD").trim());
//            log.info("sCode {}", sCode);
            if (sCode.getEmployee() == null || !employee.getId().equals(sCode.getEmployee().getId())) {
//                log.info("Save SCode");
                sCode.setEmployee(employee);
                sCodeService.saveSCode(sCode);
            }
        }
    }
}
