package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.WorkedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryWorkedRepository implements WorkedRepository {

    private final Map<String, List<Worked>> repository = new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(InMemoryWorkedRepository.class);

    public InMemoryWorkedRepository() {
        repository.put(InMemoryEmployeeRepository.EMP_11_UUID, List.of(new Worked(LocalDate.of(2021, 12, 1)
//                        , LocalDate.of(2022, 9, 5)),
//                new Worked(LocalDate.of(2022, 9,15)
                )));
        Month month = LocalDate.now().getMonth();

        repository.put(InMemoryEmployeeRepository.EMP_12_UUID, List.of(new Worked()));
        repository.put(InMemoryEmployeeRepository.EMP_13_UUID, List.of());//new Worked(LocalDate.of(2021, 12, 1)));
        repository.put(InMemoryEmployeeRepository.EMP_14_UUID, List.of(new Worked(LocalDate.of(2021, month.plus(2), 1), LocalDate.of(2022, month.minus(4),30))));
        repository.put(InMemoryEmployeeRepository.EMP_15_UUID, List.of(new Worked(LocalDate.of(2022, month, 6))));
        repository.put(InMemoryEmployeeRepository.EMP_16_UUID, List.of(new Worked(LocalDate.of(2021, month.plus(2), 1))));
        repository.put(InMemoryEmployeeRepository.EMP_17_UUID, List.of(new Worked(LocalDate.of(2021, month.plus(2), 1))));
        repository.put(InMemoryEmployeeRepository.EMP_18_UUID, List.of(new Worked(LocalDate.of(2021, month.plus(2), 1))));
        repository.put(InMemoryEmployeeRepository.EMP_19_UUID, List.of(new Worked(LocalDate.of(2022, month.minus(2), 1)
//                        , LocalDate.of(2022, 9, 2)),
//                new Worked(LocalDate.of(2022,9, 12), LocalDate.of(2022, 9, 14)),
//                new Worked(LocalDate.of(2022, 9, 21)
                )));
    }

    @Override
    public Optional<Worked> saveWorked(Worked worked) {
        String employeeId = worked.getEmployee().getId();
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
                return Optional.of(worked);
            } else {
                if (lastWorked == null) {
                    System.out.println(2);
                    workers.add(worked);
                    repository.put(employeeId, workers);
                    System.out.println("workers 2 InMemoryWorkedRepository save");
                    workers.forEach(System.out::println);
                    return Optional.of(worked);
                } else {
                    System.out.println(3);
                    workers.set(workers.indexOf(lastWorked), worked);
                    repository.put(employeeId, workers);
                    System.out.println("workers 3 InMemoryWorkedRepository save");
                    workers.forEach(System.out::println);
                    return Optional.of(worked);
                }
            }
        } else {
            if (lastWorked.getRecruitment().isEqual(worked.getRecruitment())) {
                System.out.println(4);
                workers.set(workers.indexOf(lastWorked), worked);
                repository.put(employeeId, workers);
                System.out.println("workers 4 InMemoryWorkedRepository save");
                workers.forEach(System.out::println);
                return Optional.of(worked);
            } else {
                System.out.println("Исправление даты приема пока нет");
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Worked> getFirstWorkedForThePeriod(LocalDate startDate, LocalDate endDate) {
        List<Worked> result = new ArrayList<>();
        repository.forEach((k, v) -> {
            // Recruitment - прием на работу
            // Dismissal   - увольнение
            v.stream()
                    .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(endDate)
                            && (worked.getDismissal() == null || worked.getDismissal().isAfter(startDate)))
                    .findFirst()
                    .ifPresent(result::add);
        });
//        result.values().forEach(System.out::println);
        return result;
    }

    @Override
    public Map<String, List<Worked>> getAllWorkersBetween(LocalDate startDate, LocalDate endDate) {
        Map<String, List<Worked>> result = new HashMap<>();
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
    public List<Worked> getAllWorkers() {
        List<Worked> result = new ArrayList<>();
        repository.forEach((k, v) -> {
            // Recruitment - прием на работу
            // Dismissal   - увольнение
            v.stream()
                    .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
//                    .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(LocalDate.now())
//                            && worked.getDismissal() == null)
                    .findFirst()
                    .ifPresent(worked -> result.add(worked));
        });
//        result.values().forEach(System.out::println);
        return result;
    }

    @Override
    public Optional<Worked> getLastWorkedByEmployeeId(String employeeId) {
        return repository.get(employeeId).stream()
                .filter(worked -> worked.getDismissal() == null)
                .sorted(Comparator.comparing(Worked::getRecruitment).reversed())
                .findFirst();
    }

//    @Override
//    public List<Worked> getHistory(String id) {
//        return repository.get(id).stream()
//                .sorted(Comparator.comparing(Worked::getRecruitment))
//                .collect(Collectors.toList());
//    }
}
