package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Worked;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@AllArgsConstructor
public class WorkedRepositoryImpl implements WorkedRepository {

    private final JpaWorkedRepository workedRepository;

    @Override
    public Optional<Worked> saveWorked(Worked worked) {
        Optional<Worked> optionalWorked = Optional.empty();
        if (worked.getId() == null || worked.getId().isEmpty()) {
            optionalWorked = workedRepository.findFirstByEmployeeAndRecruitment(
                    worked.getEmployee(), worked.getRecruitment());
        }
        optionalWorked.ifPresent(value -> worked.setId(value.getId()));
        try {
            return Optional.of(workedRepository.save(worked));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Worked> getFirstWorkedForThePeriod(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public Map<String, List<Worked>> getAllWorkersBetween(LocalDate startDate, LocalDate endDate) {
        Map<String, List<Worked>> result = new HashMap<>();
        workedRepository.findAll().stream()
                .filter(worked -> worked.getRecruitment() != null && worked.getRecruitment().isBefore(endDate)
                        && (worked.getDismissal() == null || worked.getDismissal().isAfter(startDate)))
                .forEach(worked -> {
                    List<Worked> workers = result.get(worked.getEmployee().id());
                    if (workers == null) {
                        workers = new ArrayList<>();
                    }
                    workers.add(worked);
                    result.put(worked.getEmployee().id(), workers);
                });
        return result;
    }

    @Override
    public List<Worked> getAllWorkers() {
        List<Worked> workedList = workedRepository.findAllWithMaxRecruitment();
//        workedList.forEach(worked -> System.out.println(worked.getId() + " " + worked.getRecruitment() + " " + worked.getDismissal()));
        return workedList;
    }

    @Override
    public Optional<Worked> getLastWorkedByEmployeeId(String employeeId) {
        return workedRepository.findFirstByEmployeeIdOrderByRecruitmentDesc(employeeId);
    }

}
