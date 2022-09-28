package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.repository.EmployeeRepository;
import com.lvv.ttimpex2.repository.TimeStampRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemoryTimeStampRepository  implements TimeStampRepository {
    private final Map<String, TimeStamp> map = new ConcurrentHashMap<>();
    private final EmployeeRepository employeeRepository;
    private static final Logger log = LoggerFactory.getLogger(InMemoryTimeStampRepository.class);

    public InMemoryTimeStampRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 1, 9, 12), 1, 1));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 1, 18, 50), 1, 0));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 2, 8, 55), 1, 1));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 2, 19, 02), 1, 0));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 3, 9, 0), 1, 1));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 4, 9, 30), 1, 1));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 4, 18, 45), 1, 0));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 5, 8, 45), 1, 1));
        save(new TimeStamp(employeeRepository.get(11), LocalDateTime.of(2022, 9, 5, 19, 5), 1, 0));
    }

    @Override
    public TimeStamp save(TimeStamp entity) {
        log.info("save TimeStamp {}", entity);
        Objects.requireNonNull(entity, "Entity must not be null");
        if (!map.containsKey(entity.getId())) {
            map.put(entity.getId(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getId(), (id, oldT) -> entity);
    }

    @Override
    public boolean delete(String id) {
        log.info("delete TimeStamp {}", id);
        return map.remove(id) != null;
    }

    @Override
    public TimeStamp get(String id) {
        log.info("get TimeStamp {}", id);
        return map.get(id);
    }

    @Override
    public Collection<TimeStamp> getCollection() {
        log.info("getAll TimeStamp");
        return map.values();
    }

    @Override
    public Map<String, LocalTime> getFirstAndLastByDateAndEmployee(LocalDate localDate, Employee employee) {
        Map<String, LocalTime> result = new HashMap<>();
        List<TimeStamp> timeStamps = map.values().stream()
                .filter(timeStamp -> timeStamp.getDate().isEqual(localDate) && timeStamp.getEmployee().equals(employee))
                .sorted(Comparator.comparing(TimeStamp::getTime))
                .toList();
        if (!timeStamps.isEmpty()) {
            result.put("first", timeStamps.get(0).getTime());
        }
        if (timeStamps.size() > 1) {
            result.put("last", timeStamps.get(timeStamps.size() - 1).getTime());
        }
        return result;
    }
}
