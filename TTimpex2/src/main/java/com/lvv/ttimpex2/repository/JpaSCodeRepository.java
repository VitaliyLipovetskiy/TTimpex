package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.SCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSCodeRepository extends JpaRepository<SCode, String> {
    List<SCode> findAllByEmployeeId(String employeeId);
}
