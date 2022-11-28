package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.dto.InitTimeStampDto;
import com.lvv.ttimpex2.utils.UtilsDB;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

@AllArgsConstructor
@Slf4j
@Service
public class InitService {
    private final EmployeeService employeeService;
    private final SCodeService sCodeService;
    private final TimeStampService timeStampService;
    private final Properties externalProperties = new Properties();

    public Boolean checkingEmployeesWithParadoxDB() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_VIPS.DB");
        log.info("pathDB_Card={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, employeeService, null);
        } else {
            log.error("Files.notExists {}", pathDB);
            return false;
        }
        return true;
    }

    public Boolean checkSCodeWithParadoxDB() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_SC.DB");
        log.info("pathDB_SCode={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, sCodeService, null);
        } else {
            log.error("Files.notExists {}", pathDB);
            return false;
        }
        return true;
    }

    public Boolean checkingTimeStampWithParadoxDB(InitTimeStampDto dto) {
        String path = UtilsDB.pathDB(externalProperties);
        for (LocalDate localDate = dto.getStartDate(); localDate.isBefore(dto.getEndDate().plusDays(1)); localDate = localDate.plusDays(1)) {
            String year = String.valueOf(localDate.getYear()).substring(2);
            String month = String.valueOf(localDate.getMonthValue());
            String day = String.valueOf(localDate.getDayOfMonth());
            String fileDB = "D" + (day.length() == 1 ? "0" : "") + day + (month.length() == 1 ? "0" : "") + month + "_" + year;
            Path pathDB = Paths.get( path + fileDB + ".DB");
            if (Files.exists(pathDB)) {
                log.info(pathDB.toString());
                ParadoxService.tableParadoxHandler(pathDB, timeStampService, localDate);
            } else {
                log.error("Files.notExists {}", pathDB);
            }
        }
        return true;
    }
}
