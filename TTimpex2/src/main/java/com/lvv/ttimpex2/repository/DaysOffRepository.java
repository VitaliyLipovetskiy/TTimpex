package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.to.DayOffTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
//    List<DayOff> getAll(int employeeId);

//    List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId);

    Map<Integer, Map<LocalDate, DayOffTo>> getAllEmployeeBetween(LocalDate startDate, LocalDate endDate);

    void update(int employeeId, DayOff dayOff);
}
