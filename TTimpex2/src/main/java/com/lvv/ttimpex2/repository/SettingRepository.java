package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Setting;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SettingRepository {
    List<Setting> getAllSettings();
    Optional<Setting> getSettingById(LocalDate date);
    Optional<Setting> getSettingByDate(LocalDate date);
    Optional<Setting> saveSetting(Setting entity);
}
