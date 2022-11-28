package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.molel.TimeStampDate;
import com.lvv.ttimpex2.repository.TimeStampDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.lvv.ttimpex2.repository.inmemory.InMemoryEmployeeRepository.*;

@Repository
public class InMemoryTimeStampDateRepository implements TimeStampDateRepository {
    private final Map<EmployeeDate, TimeStampDate> map = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemoryTimeStampDateRepository.class);

    public InMemoryTimeStampDateRepository() {
        Month month = LocalDate.now().getMonth();
        EmployeeDate employeeDate1 = new EmployeeDate(EMP_11, LocalDate.of(2022, month, 1));
        map.put(employeeDate1, new TimeStampDate(employeeDate1,null, LocalTime.of(18,25), null));
        EmployeeDate employeeDate2 = new EmployeeDate(EMP_11, LocalDate.of(2022, month, 4));
        map.put(employeeDate2, new TimeStampDate(employeeDate2, LocalTime.of(10,15), LocalTime.of(18,45), 1500));
    }

    @Override
    public TimeStampDate save(TimeStampDate entity) {
        log.info("save TimeStampDate {}", entity);
        Objects.requireNonNull(entity, "Entity must not be null");
        if (!map.containsKey(entity.getEmployeeDate())) {
            map.put(entity.getEmployeeDate(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getEmployeeDate(), (key, value) -> entity);
    }

    @Override
    public boolean delete(EmployeeDate employeeDate) {
        log.info("delete TimeStampDate {}", employeeDate);
        return map.remove(employeeDate) != null;
    }

    @Override
    public TimeStampDate get(EmployeeDate employeeDate) {
//        log.info("get TimeStampDate {}", employeeDate);
        return map.get(employeeDate);
    }

    @Override
    public Collection<TimeStampDate> getCollection() {
        log.info("getAll TimeStampDate");
        return map.values();
    }
}
