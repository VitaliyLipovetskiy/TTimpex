package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.dto.SettingDto;
import com.lvv.ttimpex2.dto.UpdateSettingDto;
import com.lvv.ttimpex2.molel.Setting;
import com.lvv.ttimpex2.repository.SettingRepository;
import com.lvv.ttimpex2.validation.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SettingService {
    private SettingRepository settingRepository;

    public List<Setting> getAllSettings() {
        return settingRepository.getAllSettings();
    }

    public Setting getSettingByDate(LocalDate localDate) {
        return settingRepository.getSettingByDate(localDate).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to find setting by " + localDate));
    }

    public Setting saveSetting(Setting setting) {
        return settingRepository.saveSetting(setting).orElseThrow(
                () -> new ApplicationException(HttpStatus.NOT_FOUND, "Unable to save setting")
        );
    }

    @Transactional
    public Setting createSetting(UpdateSettingDto dto) {
        Setting setting = new Setting();
        setting.setDate(LocalDate.now());
        setting.setStartTime(dto.getStartTime());
        setting.setEndTime(dto.getEndTime());
        setting.setPenaltyBeingLate(dto.getPenaltyBeingLate());
        setting.setPenaltyEarlyCare(dto.getPenaltyEarlyCare());
        setting.setPenaltyForMissingTimestamp(dto.getPenaltyForMissingTimestamp());
        setting.setPenaltyAbsenteeism(dto.getPenaltyAbsenteeism());
        return saveSetting(setting);
    }

    @Transactional
    public Setting updateSettingByDate(LocalDate date, SettingDto dto) {
        Setting setting = getSettingByDate(date);
        if (dto.getStartTime() != null) setting.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) setting.setEndTime(dto.getEndTime());
        if (dto.getPenaltyBeingLate() != null) setting.setPenaltyBeingLate(dto.getPenaltyBeingLate());
        if (dto.getPenaltyEarlyCare() != null) setting.setPenaltyEarlyCare(dto.getPenaltyEarlyCare());
        if (dto.getPenaltyForMissingTimestamp() != null) setting.setPenaltyForMissingTimestamp(dto.getPenaltyForMissingTimestamp());
        if (dto.getPenaltyAbsenteeism() != null) setting.setPenaltyAbsenteeism(dto.getPenaltyAbsenteeism());
        return saveSetting(setting);
    }
}
