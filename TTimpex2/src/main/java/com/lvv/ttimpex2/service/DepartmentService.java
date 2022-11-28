package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.dto.UpdateDepartmentDto;
import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.repository.DepartmentRepository;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.getAll();
    }

    public Department getDepartmentById(UUID id) {
        return departmentRepository.getById(String.valueOf(id)).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find department")
        );
    }

    @Transactional
    public Department createDepartment(Department department) {
        Assert.notNull(department, "Department must not be null");
//        if (department.getId() != null) {
//            getDepartmentById(UUID.fromString(department.getId()));
//        }
        return departmentRepository.save(department).orElseThrow(
                () -> new ApplicationException(HttpStatus.CONFLICT, "Unable to save department")
        );
    }

    @Transactional
    public Department deleteDepartmentById(UUID departmentId) {
        return departmentRepository.deleteById(departmentId.toString()).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find department")
        );
    }

    @Transactional
    public Department updateDepartmentById(UUID departmentId, UpdateDepartmentDto departmentDto) {
        Department department = getDepartmentById(departmentId);
        department.setName(departmentDto.getName());
        return departmentRepository.save(department).orElseThrow(
                () -> new ApplicationException(HttpStatus.CONFLICT, "Unable to save department")
        );
    }
}
