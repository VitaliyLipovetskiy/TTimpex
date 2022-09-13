package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryDaysOffRepository implements DaysOffRepository {

    private final Map<Integer, Map<LocalDate, DayOff>> map = new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(InMemoryDaysOffRepository.class);

    {
        Map<LocalDate, DayOff> daysOff = new HashMap<>();
        for (LocalDate date = LocalDate.of(2022, 1, 1); date.isBefore(LocalDate.of(2023, 1, 1)); date = date.plusDays(1)) {
            daysOff.put(date, new DayOff(date, false));
        }

        for (int i = 11; i <= 19; i++) {
            map.put(i, daysOff);
        }
    }

    @Override
    public List<DayOff> getAll(int employeeId) {
        return map.get(employeeId).values().stream().toList();
    }

    @Override
    public List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId) {
        return map.get(employeeId).values().stream()
                .filter(dayOff ->
                    dayOff.getDate().isAfter(startDate.minusDays(1)) && dayOff.getDate().isBefore(endDate.plusDays(1)))
                .collect(Collectors.toList());
    }
}
