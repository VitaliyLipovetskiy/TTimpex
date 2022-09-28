package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.molel.TimeStampDate;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.TimeStampDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryTimeStampDateRepository implements TimeStampDateRepository {
    private final Map<EmployeeDate, TimeStampDate> map = new ConcurrentHashMap<>();
    private final EmployeeRepository employeeRepository;
    private static final Logger log = LoggerFactory.getLogger(InMemoryTimeStampDateRepository.class);

    public InMemoryTimeStampDateRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        save(new TimeStampDate(new EmployeeDate(employeeRepository.get(11), LocalDate.of(2022, 9, 1)),
                null, LocalTime.of(18,25), null));
        save(new TimeStampDate(new EmployeeDate(employeeRepository.get(11), LocalDate.of(2022, 9, 4)),
                LocalTime.of(10,15), LocalTime.of(18,45), 1500));




    }

    @Override
    public TimeStampDate save(TimeStampDate entity) {
        log.info("save TimeStampDate {}", entity);
        Objects.requireNonNull(entity, "Entity must not be null");
        if (!map.containsKey(entity.getEmployeeDate())) {
            map.put(entity.getEmployeeDate(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getEmployeeDate(), (id, oldT) -> entity);
    }

    @Override
    public boolean delete(EmployeeDate employeeDate) {
        log.info("delete TimeStampDate {}", employeeDate);
        return map.remove(employeeDate) != null;
    }

    @Override
    public TimeStampDate get(EmployeeDate employeeDate) {
        log.info("get TimeStampDate {}", employeeDate);
        return map.get(employeeDate);
    }

    @Override
    public Collection<TimeStampDate> getCollection() {
        log.info("getAll TimeStampDate");
        return map.values();
    }
}
