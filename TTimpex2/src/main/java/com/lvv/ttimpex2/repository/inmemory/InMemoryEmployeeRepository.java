package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.repository.DepartmentRepository;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryEmployeeRepository extends InMemoryBaseRepository<Employee> implements EmployeeRepository {
    private DepartmentRepository departmentRepository;

    private static final Logger log = LoggerFactory.getLogger(InMemoryEmployeeRepository.class);

    public InMemoryEmployeeRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        save(new Employee("Иванов Иван", departmentRepository.get(1), "0012", true, LocalTime.of(10,15), LocalTime.of(18,30)));
        save(new Employee("Сидоров Сидор", departmentRepository.get(1), null, true, null, null));
        save(new Employee("Петров Петр", departmentRepository.get(1), "0035", true, null, null));
        save(new Employee("Васечкин Вася", departmentRepository.get(2), "0067", false, LocalTime.of(9,45), LocalTime.of(18,0)));
        save(new Employee("Андреев Андрей", departmentRepository.get(2), "0039"));
        save(new Employee("Смирнов Сергей", departmentRepository.get(2), "0072"));
        save(new Employee("Сергеев Антон", departmentRepository.get(2), "0006"));
        save(new Employee("Сычев Дмитрий"));
        save(new Employee("Шпаковский Виктор"));
    }

    @Override
    public boolean worked(int id) {
        log.info("worked {}", id);
        Employee employee = get(id);
        if (employee == null) {
            return false;
        } else {
            employee.setWorked(!employee.getWorked());
            return true;
        }
    }

    @Override
    public List<Employee> getAll() {
        log.info("getAll employees");
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Employee> getAllWorked() {
        log.info("getAll employees");
        return new ArrayList<>(map.values().stream()
                .filter(Employee::getWorked)
                .toList());
    }
}
