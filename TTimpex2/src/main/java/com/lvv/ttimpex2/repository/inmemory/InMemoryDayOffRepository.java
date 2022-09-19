package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
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
public class InMemoryDayOffRepository implements DaysOffRepository {

    private final Map<Integer, Map<LocalDate, DayOff>> map = new ConcurrentHashMap<>();

    private final WorkedRepository workedRepository;

    private static final Logger log = LoggerFactory.getLogger(InMemoryDayOffRepository.class);

    {
        for (int i = 11; i <= 19; i++) {
            Map<LocalDate, DayOff> daysOff = new ConcurrentHashMap<>();
            for (LocalDate date = LocalDate.of(2022, 1, 1); date.isBefore(LocalDate.of(2023, 1, 1)); date = date.plusDays(1)) {
                daysOff.put(date, new DayOff(date, false));
            }
            map.put(i, daysOff);
        }
    }

    public InMemoryDayOffRepository(WorkedRepository workedRepository) {
        this.workedRepository = workedRepository;
    }

    @Override
    public List<DayOff> getAll(int employeeId) {
        return map.get(employeeId).values().stream().toList();
    }

    @Override
    public List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId) {
        List<DayOff> dayOffs = map.get(employeeId).values().stream()
                .filter(dayOff ->
                        dayOff.getDate().isAfter(startDate.minusDays(1)) && dayOff.getDate().isBefore(endDate.plusDays(1)))
                .sorted(Comparator.comparing(DayOff::getDate))
                .collect(Collectors.toList());
        List<Worked> workers = workedRepository.getHistory(employeeId);

        System.out.println(employeeId + " => " + workers);

        for (DayOff day: dayOffs) {
            for (Worked w: workers) {
                day.setWorked(w.getRecruitment() != null &&
                        day.getDate().isAfter(w.getRecruitment().minusDays(1)) &&
                        (w.getDismissal() == null ||
                                day.getDate().isBefore(w.getDismissal().plusDays(1))));
            }
        }
//        dayOffs.forEach(day ->
//            workers.forEach(w ->
//                    day.setWorked(w.getRecruitment() != null &&
//                            day.getDate().isAfter(w.getRecruitment().minusDays(1)) &&
//                            (w.getDismissal() == null ||
//                            day.getDate().isBefore(w.getDismissal().plusDays(1))))
//
//            )
//        );

//        System.out.println("" + employeeId + " " + dayOffs);
        return dayOffs;
    }

    @Override
    public Map<Integer, Map<LocalDate, DayOff>> getAllEmployeeBetween(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Map<LocalDate, DayOff>> result = new ConcurrentHashMap<>();
        map.forEach((i, val) -> {
            Map<LocalDate, DayOff> newVal = val.entrySet().stream()
                    .filter(entry -> entry.getKey().isAfter(startDate.minusDays(1)) && entry.getKey().isBefore(endDate))
//                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            result.put(i, newVal);
        });
        return result;
    }

    @Override
    public void update(int employeeId, DayOff dayOff) {
        Map<LocalDate, DayOff> dayOffMap = new ConcurrentHashMap<>(map.get(employeeId));
//        System.out.println(1);
//        System.out.println(dayOffMap);
//        System.out.println(2);
//        map.forEach((i, off) -> {
//            System.out.println(i + " " + off);
//        });
        dayOffMap.put(dayOff.getDate(), dayOff);
//        System.out.println(3);
//        System.out.println(dayOffMap);
//        System.out.println(4);
//        map.forEach((i, off) -> {
//            System.out.println(i + " " + off);
//        });
        map.put(employeeId, dayOffMap);
//        System.out.println(employeeId);
//        map.forEach((i, off) -> {
//            System.out.println(i + " " + off);
//        });
//        System.out.println(dayOffMap.get(dayOff.getDate()));
//        System.out.println(getBetween(LocalDate.of(2022, 9,1), LocalDate.of(2022, 9, 30), employeeId));
    }
}
