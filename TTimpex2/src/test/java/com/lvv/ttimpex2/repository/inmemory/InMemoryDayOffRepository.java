package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.repository.DayOffRepository;
import com.lvv.ttimpex2.dto.DayOffAndWorkedDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryDayOffRepository implements DayOffRepository {
    private final Map<String, Map<LocalDate, DayOff>> map = new ConcurrentHashMap<>();

    public InMemoryDayOffRepository() {
        Month month = LocalDate.now().getMonth();
        map.put(InMemoryEmployeeRepository.EMP_11_UUID, Map.of(LocalDate.of(2022, month,2), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,2)), true),
                LocalDate.of(2022, month,3), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,3)), true),
                LocalDate.of(2022, month,8), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,8)), true),
                LocalDate.of(2022, month,9), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,9)), true)
        ));
        map.put(InMemoryEmployeeRepository.EMP_18_UUID, Map.of(LocalDate.of(2022, month,3), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,3)), true),
                LocalDate.of(2022, month,4), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,4)), true),
                LocalDate.of(2022, month,10), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,10)), true),
                LocalDate.of(2022, month,11), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,11)), true)
        ));
        map.put(InMemoryEmployeeRepository.EMP_19_UUID, Map.of(LocalDate.of(2022, month,3), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,3)), true),
                LocalDate.of(2022, month,4), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,4)), true),
                LocalDate.of(2022, month,10), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,10)), true),
                LocalDate.of(2022, month,11), new DayOff(new EmployeeDate(null, LocalDate.of(2022, month,11)), true)
        ));
    }

    @Override
    public Optional<DayOff> findById(EmployeeDate id) {
        return Optional.of(map.get(id.getEmployee().id()).get(id.getDate()));
    }

    @Override
    public List<DayOff> getAll() {
        return List.of();
    }

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
    public Map<String, Map<LocalDate, DayOffAndWorkedDto>> getAllEmployeeWithDaysOffBetween(LocalDate startDate, LocalDate endDate) {
        Map<String, Map<LocalDate, DayOffAndWorkedDto>> result = new ConcurrentHashMap<>();
        map.forEach((i, value) -> {
            Map<LocalDate, DayOffAndWorkedDto> newVal = new ConcurrentHashMap<>();
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                DayOff dayOff = value.getOrDefault(date, new DayOff(null, date));
                newVal.put(date, new DayOffAndWorkedDto(dayOff));
            }
            result.put(i, newVal);
        });
        return result;
    }

    @Override
    public DayOff saveDayOff(DayOff dayOff) {
        Map<LocalDate, DayOff> dayOffMap = new ConcurrentHashMap<>();
        String employeeId = dayOff.getEmployeeDate().getEmployee().getId();
        if (map.containsKey(employeeId)) {
            dayOffMap = new ConcurrentHashMap<>(map.get(employeeId));
        }
        dayOffMap.put(dayOff.getEmployeeDate().getDate(), dayOff);
        map.put(employeeId, dayOffMap);
        return dayOff;
    }
}
