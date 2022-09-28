package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryDepartmentRepository extends InMemoryBaseRepository<Department> implements DepartmentRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryDepartmentRepository.class);

    {
        save(new Department("Сервис 15"));
        save(new Department("Сервис 38"));
        counter.getAndSet(10);
    }

    @Override
    public List<Department> getAll() {
        log.info("getAll");
        return map.values().stream()
                .sorted(Comparator.comparing(Department::getName))
                .toList();
    }
}
