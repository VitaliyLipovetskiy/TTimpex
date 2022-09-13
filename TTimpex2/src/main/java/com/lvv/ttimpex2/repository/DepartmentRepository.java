package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Department;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface DepartmentRepository {

    Department save(Department department);

    boolean delete(int id);

    Department get(int id);

    List<Department> getAll();
}
