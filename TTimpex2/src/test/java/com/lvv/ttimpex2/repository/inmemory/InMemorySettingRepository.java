package com.lvv.ttimpex2.repository.inmemory;

import com.lvv.ttimpex2.molel.Setting;
import com.lvv.ttimpex2.repository.SettingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemorySettingRepository implements SettingRepository {
    private final Map<LocalDate, Setting> map = new ConcurrentHashMap<>();

//    private final Logger log = LoggerFactory.getLogger(getClass());

    public InMemorySettingRepository() {
        Month month = LocalDate.now().getMonth();
        map.put(LocalDate.of(2000, 1,1),
                new Setting(LocalDate.of(2022, month,1),
                LocalTime.of(9,0),
                LocalTime.of(19,0),
                500,
                200,
                300,
                        1000));
    }

    @Override
    public List<Setting> getAllSettings() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Setting> getSettingByDate(LocalDate date) {
        return map.values().stream()
                .filter(setting -> setting.getDate().isBefore(date.plusDays(1)))
                .max(Comparator.comparing(Setting::getDate));
    }

    @Override
    public Optional<Setting> getSettingById(LocalDate date) {
        return Optional.of(map.get(date));
    }

    @Override
    public Optional<Setting> saveSetting(Setting entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        map.put(entity.getDate(), entity);
        return Optional.of(entity);
    }
}
