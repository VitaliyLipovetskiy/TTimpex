package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.TimeStamp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class TimeStampRepositoryImpl implements TimeStampRepository {
    private final JpaTimeStampRepository timeStampRepository;

    @Override
    public Optional<TimeStamp> save(TimeStamp entity) {
        try {
            return Optional.of(timeStampRepository.save(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<TimeStamp> findById(String id) {
        return timeStampRepository.findById(id);
    }

    @Override
    public Collection<TimeStamp> getCollection() {
        return List.of();
    }

    @Override
    public Map<String, LocalTime> getFirstAndLastByEmployeeAndDate(Employee employee, LocalDate localDate) {
        List<TimeStamp> timeStamps = timeStampRepository.findAllByEmployeeAndDateOrderByTime(employee, localDate);
        Map<String, LocalTime> result = new HashMap<>();
        if (!timeStamps.isEmpty()) {
            result.put("first", timeStamps.get(0).getTime());
        }
        if (timeStamps.size() > 1) {
            result.put("last", timeStamps.get(timeStamps.size() - 1).getTime());
        }
        return result;
    }

    @Override
    public Optional<TimeStamp> getLastByEmployeeAndDate(Employee employee, LocalDate localDate) {
        return timeStampRepository.findFirstByEmployeeAndDateOrderByTimeDesc(employee, localDate);
    }
}
