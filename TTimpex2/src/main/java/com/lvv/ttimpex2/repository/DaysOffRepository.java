package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.DayOff;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface DaysOffRepository {

//    Employee save(Employee employee);
//
//    boolean delete(int id);
//
//    boolean worked(int id);
//
//    Employee get(int id);
//
    List<DayOff> getAll(int employeeId);

    List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId);
}
