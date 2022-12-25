package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.molel.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaSettingRepository extends JpaRepository<Setting, LocalDate> {
    List<Setting> findByDateBefore(LocalDate date);
}
