package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Setting;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
public interface SettingRepository {
    Optional<Setting> get(LocalDate date);
    void put(Setting entity);

}
