package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Worked;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
public interface WorkedRepository {

    boolean save(Worked worked);

//     boolean delete(int id);
//
//     boolean worked(int id);

    Map<Integer, Worked> getAllEmployedForThePeriod(LocalDate startDate, LocalDate endDate);

    Map<Integer, List<Worked>> getAllEmployeeBetween(LocalDate startDate, LocalDate endDate);

    Map<Integer, Worked> getAllEmployed();

    Worked getLast(int id);

    List<Worked> getHistory(int id);

//     List<Worked> getAll();
//
//     List<Worked> getAllWorked();

}
