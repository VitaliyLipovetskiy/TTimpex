package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.TimeStamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface TimeStampRepository {
    Optional<TimeStamp> save(TimeStamp entity);
    boolean delete(String id);
    Optional<TimeStamp> findById(String id);
    Collection<TimeStamp> getCollection();
    Map<String, LocalTime> getFirstAndLastByEmployeeAndDate(Employee employee, LocalDate localDate);
    Optional<TimeStamp> getLastByEmployeeAndDate(Employee employee, LocalDate localDate);
}
