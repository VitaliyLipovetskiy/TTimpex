package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaTimeStampRepository extends JpaRepository<TimeStamp, String> {

    List<TimeStamp> findAllByEmployeeAndDateOrderByTime(Employee employee, LocalDate date);
    Optional<TimeStamp> findFirstByEmployeeAndDateOrderByTimeDesc(Employee employee, LocalDate date);
}
