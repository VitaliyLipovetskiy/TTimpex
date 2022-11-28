package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lvv.ttimpex2.repository.inmemory.InMemoryDepartmentRepository.*;

@Repository
@Slf4j
public class InMemoryEmployeeRepository extends InMemoryBaseRepository<Employee> implements EmployeeRepository {

    public static final String EMP_11_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_12_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_13_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_14_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_15_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_16_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_17_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_18_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_19_UUID = String.valueOf(UUID.randomUUID());
    public static final String EMP_IVANOV_11 = "Иванов Иван";
    public static final String EMP_SIDOROV_12 = "Сидоров Сидор";
    public static final String EMP_PETROV_13 = "Петров Петр";
    public static final String EMP_VASECHKN_14 = "Васечкин Вася";
    public static final String EMP_ANDREEV_15 = "Андреев Андрей";
    public static final String EMP_SMIRNOV_16 = "Смирнов Сергей";
    public static final String EMP_SERGEEV_17 = "Сергеев Антон";
    public static final String EMP_SICHEV_18 = "Сычев Дмитрий";
    public static final String EMP_SHPAKOVSKIY_19 = "Шпаковский Виктор";

    public static final Employee EMP_11 = new Employee(EMP_11_UUID, EMP_IVANOV_11, DEP_1, "0012", LocalTime.of(10,15), LocalTime.of(18,30), true);
    public static final Employee EMP_12 = new Employee(EMP_12_UUID, EMP_SIDOROV_12, DEP_1, null, null, null);
    public static final Employee EMP_13 = new Employee(EMP_13_UUID, EMP_PETROV_13, DEP_2, "0035", null, null);
    public static final Employee EMP_14 = new Employee(EMP_14_UUID, EMP_VASECHKN_14, DEP_1, "0067", LocalTime.of(9,45), LocalTime.of(18,0));
    public static final Employee EMP_15 = new Employee(EMP_15_UUID, EMP_ANDREEV_15, DEP_2, "0039");
    public static final Employee EMP_16 = new Employee(EMP_16_UUID, EMP_SMIRNOV_16, DEP_2, "0072");
    public static final Employee EMP_17 = new Employee(EMP_17_UUID, EMP_SERGEEV_17, DEP_2, "0006", true);
    public static final Employee EMP_18 = new Employee(EMP_18_UUID, EMP_SICHEV_18);
    public static final Employee EMP_19 = new Employee(EMP_19_UUID, EMP_SHPAKOVSKIY_19);

    public InMemoryEmployeeRepository() {
        map.put(EMP_11_UUID, EMP_11);
        map.put(EMP_12_UUID, EMP_12);
        map.put(EMP_13_UUID, EMP_13);
        map.put(EMP_14_UUID, EMP_14);
        map.put(EMP_15_UUID, EMP_15);
        map.put(EMP_16_UUID, EMP_16);
        map.put(EMP_17_UUID, EMP_17);
        map.put(EMP_18_UUID, EMP_18);
        map.put(EMP_19_UUID, EMP_19);
    }

    @Override
    public List<Employee> getAll() {
        log.info("getAll employees");
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Employee> deleteById(UUID id) {
        Optional<Employee> optionalEmployee = getById(id);
        if (optionalEmployee.isPresent()) {
            map.remove(String.valueOf(id), optionalEmployee.get());
            return optionalEmployee;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> getById(UUID id) {
        return Optional.of(map.get(String.valueOf(id)));
    }

}
