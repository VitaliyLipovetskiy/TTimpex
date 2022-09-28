package com.lvv.ttimpex2.service.old;

import com.lvv.ttimpex2.molel.old.SCodeOld;
import com.lvv.ttimpex2.repository.old.SCodeOldRepository;
import com.lvv.ttimpex2.service.ParadoxService;
import com.lvv.ttimpex2.utils.UtilsDB;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
//@Service
public final class SCodeService {
    private final SCodeOldRepository repository;
    private final Properties externalProperties = new Properties();
    private static final Logger LOG = getLogger(SCodeService.class);

    public SCodeService(SCodeOldRepository repository) {
        this.repository = repository;
    }

    public List<SCodeOld> findAll() {
        return null;//repository.findAll();
    }

    public void checkSCode() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_SC.DB");
        LOG.warn("pathDB_SCode={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, new SCodeHandler(repository));
        } else {
            LOG.error("Files.notExists {}", pathDB);
        }
    }
}
