package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Setting;
import com.lvv.ttimpex2.repository.SettingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class InMemorySettingRepository implements SettingRepository {
    private final Map<LocalDate, Setting> map = new ConcurrentHashMap<>();

//    private final Logger log = LoggerFactory.getLogger(getClass());

    public InMemorySettingRepository() {
        map.put(LocalDate.of(2000, 1,1),
                new Setting(LocalDate.of(2022, 8,1),
                LocalTime.of(9,0),
                LocalTime.of(19,0),
                500,
                200,
                300,
                        1000));
    }

    public Optional<Setting> get(LocalDate date) {
        return map.values().stream()
                .filter(setting -> setting.getDate().isBefore(date.plusDays(1)))
                .max(Comparator.comparing(Setting::getDate));
    }

    public void put(Setting entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        map.put(entity.getDate(), entity);
    }
}
