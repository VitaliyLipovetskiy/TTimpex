package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.repository.WorkedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WorkedService {
    private final WorkedRepository workedRepository;

    public Worked getLastWorkedByEmployeeId(String id) {
        return workedRepository.getLastWorkedByEmployeeId(id)
                .orElse(new Worked());
    }

    public List<Worked> getFirstWorkedForThePeriod(LocalDate startDate, LocalDate endDate) {
        return workedRepository.getFirstWorkedForThePeriod(startDate, endDate);
    }

    public List<Worked> getAllWorkers() {
        return workedRepository.getAllWorkers();
    }

    @Transactional
    public void saveWorked(Worked worked) {
        if (worked != null && worked.getRecruitment() != null) {
            if (worked.getDismissal() != null && worked.getRecruitment().isAfter(worked.getDismissal())) {
                log.error("Wrong date recruitment or dismissal");
                return;
            }
            workedRepository.saveWorked(worked);
        }
    }
}
