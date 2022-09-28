package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.DaysOffRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
import com.lvv.ttimpex2.to.DayOffTo;
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

    public InMemoryDayOffRepository() {
        map.put(11, Map.of(LocalDate.of(2022, 9,2), new DayOff(null, LocalDate.of(2022, 9,2), true),
                LocalDate.of(2022, 9,3), new DayOff(null, LocalDate.of(2022, 9,3), true),
                LocalDate.of(2022, 9,8), new DayOff(null, LocalDate.of(2022, 9,8), true),
                LocalDate.of(2022, 9,9), new DayOff(null, LocalDate.of(2022, 9,9), true)
        ));
        map.put(18, Map.of(LocalDate.of(2022, 9,3), new DayOff(null, LocalDate.of(2022, 9,3), true),
                LocalDate.of(2022, 9,4), new DayOff(null, LocalDate.of(2022, 9,4), true),
                LocalDate.of(2022, 9,10), new DayOff(null, LocalDate.of(2022, 9,10), true),
                LocalDate.of(2022, 9,11), new DayOff(null, LocalDate.of(2022, 9,11), true)
        ));
        map.put(19, Map.of(LocalDate.of(2022, 9,3), new DayOff(null, LocalDate.of(2022, 9,3), true),
                LocalDate.of(2022, 9,4), new DayOff(null, LocalDate.of(2022, 9,4), true),
                LocalDate.of(2022, 9,10), new DayOff(null, LocalDate.of(2022, 9,10), true),
                LocalDate.of(2022, 9,11), new DayOff(null, LocalDate.of(2022, 9,11), true)
        ));
    }

    //    @Override
//    public List<DayOff> getAll(int employeeId) {
//        return map.get(employeeId).values().stream().toList();
//    }

//    @Override
//    public List<DayOff> getBetween(LocalDate startDate, LocalDate endDate, int employeeId) {
//        List<DayOff> dayOffs = map.get(employeeId).values().stream()
//                .filter(dayOff ->
//                        dayOff.getDate().isAfter(startDate.minusDays(1)) && dayOff.getDate().isBefore(endDate.plusDays(1)))
//                .sorted(Comparator.comparing(DayOff::getDate))
//                .collect(Collectors.toList());
//        List<Worked> workers = workedRepository.getHistory(employeeId);
//
//        System.out.println(employeeId + " => ");
//        workers.forEach(System.out::println);
//
//        for (DayOff day: dayOffs) {
//            for (Worked w: workers) {
//                day.setWorked(w.getRecruitment() != null &&
//                        day.getDate().isAfter(w.getRecruitment().minusDays(1)) &&
//                        (w.getDismissal() == null ||
//                                day.getDate().isBefore(w.getDismissal().plusDays(1))));
//            }
//        }
////        dayOffs.forEach(day ->
////            workers.forEach(w ->
////                    day.setWorked(w.getRecruitment() != null &&
////                            day.getDate().isAfter(w.getRecruitment().minusDays(1)) &&
////                            (w.getDismissal() == null ||
////                            day.getDate().isBefore(w.getDismissal().plusDays(1))))
////
////            )
////        );
//
////        System.out.println("" + employeeId + " " + dayOffs);
//        return dayOffs;
//    }

    @Override
    public Map<Integer, Map<LocalDate, DayOffTo>> getAllEmployeeBetween(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Map<LocalDate, DayOffTo>> result = new ConcurrentHashMap<>();
        map.forEach((i, value) -> {
            Map<LocalDate, DayOffTo> newVal = new ConcurrentHashMap<>();
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                DayOff dayOff = value.getOrDefault(date, new DayOff(date));
                newVal.put(date, new DayOffTo(dayOff));
            }
            result.put(i, newVal);
        });
        return result;
    }

    @Override
    public void update(int employeeId, DayOff dayOff) {
        Map<LocalDate, DayOff> dayOffMap = new ConcurrentHashMap<>();
        if (map.containsKey(employeeId)) {
            dayOffMap = new ConcurrentHashMap<>(map.get(employeeId));
        }
        dayOffMap.put(dayOff.getDate(), dayOff);
        map.put(employeeId, dayOffMap);
    }
}
