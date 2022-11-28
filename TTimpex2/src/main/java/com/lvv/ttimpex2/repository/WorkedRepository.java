package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Worked;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkedRepository {

    Optional<Worked> saveWorked(Worked worked);

//     boolean delete(int id);
//
//     boolean worked(int id);

    List<Worked> getFirstWorkedForThePeriod(LocalDate startDate, LocalDate endDate);

    Map<String, List<Worked>> getAllWorkersBetween(LocalDate startDate, LocalDate endDate);

    List<Worked> getAllWorkers();

    Optional<Worked> getLastWorkedByEmployeeId(String id);

//    List<Worked> getHistory(String id);

//     List<Worked> getAll();
//
//     List<Worked> getAllWorked();

}
