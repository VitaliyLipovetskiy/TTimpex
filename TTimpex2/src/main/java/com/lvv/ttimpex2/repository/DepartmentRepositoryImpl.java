package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Department;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final JpaDepartmentRepository departmentRepository;

    @Override
    public Optional<Department> save(Department department) {
        try {
            return Optional.of(departmentRepository.save(department));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Department> deleteById(String id) {
        Optional<Department> optionalDepartment = getById(id);
        if (optionalDepartment.isPresent()) {
            departmentRepository.delete(optionalDepartment.get());
            return optionalDepartment;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> getById(String id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }
}
