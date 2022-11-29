package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.dto.DayOffAndWorkedDto;
import com.lvv.ttimpex2.molel.EmployeeDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DayOffRepository {

//    boolean delete(int id);
//
//    boolean worked(int id);
//
    Optional<DayOff> findById(EmployeeDate id);
//
//    List<DayOff> getAll(int employeeId);

//    List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId);

    Map<String, Map<LocalDate, DayOffAndWorkedDto>> getAllEmployeeWithDaysOffBetween(LocalDate startDate, LocalDate endDate);

    DayOff saveDayOff(DayOff dayOff);

    List<DayOff> getAll();
}
