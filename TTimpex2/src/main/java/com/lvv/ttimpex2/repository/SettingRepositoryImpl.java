package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Setting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SettingRepositoryImpl implements SettingRepository {
    private final JpaSettingRepository settingRepository;

    @Override
    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    @Override
    public Optional<Setting> getSettingById(LocalDate date) {
        return settingRepository.findById(date);
    }

    @Override
    public Optional<Setting> getSettingByDate(LocalDate date) {
        return settingRepository.findByDateBefore(date).stream().max(Comparator.comparing(Setting::getDate));
    }

    @Override
    public Optional<Setting> saveSetting(Setting entity) {
        try {
            return Optional.of(settingRepository.save(entity));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
