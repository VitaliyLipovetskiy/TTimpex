package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.WorkedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryWorkedRepository implements WorkedRepository {

    private final Map<Integer, List<Worked>> repository = new ConcurrentHashMap<>();
    @Autowired
    private InMemoryEmployeeRepository employeeRepository;

    private static final Logger log = LoggerFactory.getLogger(InMemoryWorkedRepository.class);

    {
        repository.put(11, List.of(new Worked(LocalDate.of(2021, 12, 1)
//                        , LocalDate.of(2022, 9, 5)),
//                new Worked(LocalDate.of(2022, 9,15)
                )));
        repository.put(12, List.of(new Worked()));
        repository.put(13, List.of());//new Worked(LocalDate.of(2021, 12, 1)));
        repository.put(14, List.of(new Worked(LocalDate.of(2021, 12, 1), LocalDate.of(2022, 6,30))));
        repository.put(15, List.of(new Worked(LocalDate.of(2022, 9, 6))));
        repository.put(16, List.of(new Worked(LocalDate.of(2021, 12, 1))));
        repository.put(17, List.of(new Worked(LocalDate.of(2021, 12, 1))));
        repository.put(18, List.of(new Worked(LocalDate.of(2021, 12, 1))));
        repository.put(19, List.of(new Worked(LocalDate.of(2022, 8, 1)
//                        , LocalDate.of(2022, 9, 2)),
//                new Worked(LocalDate.of(2022,9, 12), LocalDate.of(2022, 9, 14)),
//                new Worked(LocalDate.of(2022, 9, 21)
                )));
    }

    @Override
    public boolean save(Worked worked) {
        Integer employeeId = worked.getEmployee().getId();
        if (!repository.containsKey(employeeId)) {
            repository.put(employeeId, new ArrayList<>());
        }
        List<Worked> workers = new ArrayList<>(repository.get(employeeId));
        System.out.println("workers 1 InMemoryWorkedRepository save");
        workers.forEach(System.out::println);
        Worked lastWorked = workers.stream()
                .filter(worker -> worker.getDismissal() == null)
                .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                .findFirst().orElse(null);
        System.out.println("lastWorked " + lastWorked);
        if (lastWorked == null || lastWorked.getRecruitment() == null && lastWorked.getDismissal() == null) {
            if (worked.getRecruitment() == null) {
                System.out.println(1);
                workers.add(worked);
                repository.put(employeeId, workers);
                return true;
            } else {
                if (lastWorked == null) {
                    System.out.println(2);
                    workers.add(worked);
                    repository.put(employeeId, workers);
                    System.out.println("workers 2 InMemoryWorkedRepository save");
                    workers.forEach(System.out::println);
                    return true;
                } else {
                    System.out.println(3);
                    workers.set(workers.indexOf(lastWorked), worked);
                    repository.put(employeeId, workers);
                    System.out.println("workers 3 InMemoryWorkedRepository save");
                    workers.forEach(System.out::println);
                    return true;
                }
            }
        } else {
            if (lastWorked.getRecruitment().isEqual(worked.getRecruitment())) {
                System.out.println(4);
                workers.set(workers.indexOf(lastWorked), worked);
                repository.put(employeeId, workers);
                System.out.println("workers 4 InMemoryWorkedRepository save");
                workers.forEach(System.out::println);
                return true;
            } else {
                System.out.println("Исправление даты приема пока нет");
                return false;
            }
        }
    }

    @Override
    public Map<Integer, Worked> getAllEmployedForThePeriod(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Worked> result = new HashMap<>();
        repository.forEach((k, v) -> {
            // Recruitment - прием на работу
            // Dismissal   - увольнение
            v.stream()
                    .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(endDate)
                            && (worked.getDismissal() == null || worked.getDismissal().isAfter(startDate)))
                    .findFirst()
                    .ifPresent(worked -> result.put(k, worked));
        });
//        result.values().forEach(System.out::println);
        return result;
    }

    @Override
    public Map<Integer, List<Worked>> getAllEmployeeBetween(LocalDate startDate, LocalDate endDate) {
        Map<Integer, List<Worked>> result = new HashMap<>();
        repository.forEach((k, v) -> {
            // Recruitment - прием на работу
            // Dismissal   - увольнение
            v.stream()
                    .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(endDate)
                            && (worked.getDismissal() == null || worked.getDismissal().isAfter(startDate)))
                    .forEach(worked -> {
                        List<Worked> workers = result.get(k);
                        if (workers == null) {
                            workers = new ArrayList<>();
                        }
                        workers.add(worked);
                        result.put(k, workers);
                    });
        });
        return result;
    }

    @Override
    public Map<Integer, Worked> getAllEmployed() {
        Map<Integer, Worked> result = new HashMap<>();
        repository.forEach((k, v) -> {
            // Recruitment - прием на работу
            // Dismissal   - увольнение
            v.stream()
                    .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
//                    .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(LocalDate.now())
//                            && worked.getDismissal() == null)
                    .findFirst()
                    .ifPresent(worked -> result.put(k, worked));
        });
//        result.values().forEach(System.out::println);
        return result;
    }

    @Override
    public Worked getLast(int id) {
        return repository.get(id).stream()
                .filter(worked -> worked.getDismissal() == null)
                .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                .findFirst()
                .orElse(new Worked());
    }

    @Override
    public List<Worked> getHistory(int id) {
        return new ArrayList<>(repository.get(id).stream()
                .sorted(Comparator.comparing(Worked::getRecruitment)).toList());
    }
}
