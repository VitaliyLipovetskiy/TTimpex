package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class InMemoryDepartmentRepository extends InMemoryBaseRepository<Department> implements DepartmentRepository {
    public static final String DEP_1_UUID = String.valueOf(UUID.randomUUID());
    public static final Department DEP_1 = new Department(DEP_1_UUID, "Сервис 15");
    public static final String DEP_2_UUID = String.valueOf(UUID.randomUUID());
    public static final Department DEP_2 = new Department(DEP_2_UUID, "Сервис 38");

    public InMemoryDepartmentRepository() {
        map.put(DEP_1_UUID, DEP_1);
        map.put(DEP_2_UUID, DEP_2);
    }

    @Override
    public List<Department> getAll() {
        log.info("getAll");
        return map.values().stream()
                .sorted(Comparator.comparing(Department::getName))
                .collect(Collectors.toList());
    }
}
