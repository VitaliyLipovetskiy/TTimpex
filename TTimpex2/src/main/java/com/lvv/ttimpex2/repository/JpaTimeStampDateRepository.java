package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStampDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTimeStampDateRepository extends JpaRepository<TimeStampDate, EmployeeDate> {
}
