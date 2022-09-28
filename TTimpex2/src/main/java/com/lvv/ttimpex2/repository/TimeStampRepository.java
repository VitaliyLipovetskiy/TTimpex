package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.TimeStamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
public interface TimeStampRepository {
    TimeStamp save(TimeStamp entity);
    boolean delete(String id);
    TimeStamp get(String id);
    Collection<TimeStamp> getCollection();
    Map<String, LocalTime> getFirstAndLastByDateAndEmployee(LocalDate localDate, Employee employee);
}
