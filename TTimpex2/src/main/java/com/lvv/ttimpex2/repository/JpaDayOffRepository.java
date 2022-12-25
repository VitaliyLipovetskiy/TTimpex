package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.EmployeeDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDayOffRepository extends JpaRepository<DayOff, EmployeeDate> {
}
