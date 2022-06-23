package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;
import com.lvv.ttimpex2.service.handlers.SCodeHandler;
import com.lvv.ttimpex2.service.handlers.TimeStampHandler;
import com.lvv.ttimpex2.utils.UtilsDB;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class SCodeService {
    final private SCodeRepository repository;
    final private Properties externalProperties = new Properties();
    final static private Logger log = getLogger(SCodeService.class);

    public SCodeService(SCodeRepository repository) {
        this.repository = repository;
    }

    public List<SCode> findAll() {
        return repository.findAll();
    }

    public void checkSCode() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_SC.DB");
        log.warn("pathDB_SCode={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, new SCodeHandler(repository));
        } else {
            log.error("Files.notExists {}", pathDB);
        }
    }
}
