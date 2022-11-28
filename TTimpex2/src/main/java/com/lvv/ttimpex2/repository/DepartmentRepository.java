package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    Optional<Department> save(Department department);

    Optional<Department> deleteById(String id);

    Optional<Department> getById(String id);

    List<Department> getAll();
}
