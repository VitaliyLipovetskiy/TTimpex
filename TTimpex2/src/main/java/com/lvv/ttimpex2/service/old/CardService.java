package com.lvv.ttimpex2.service.old;

import com.lvv.ttimpex2.molel.old.CardOld;
import com.lvv.ttimpex2.repository.old.CardOldRepository;
import com.lvv.ttimpex2.service.ParadoxService;
import com.lvv.ttimpex2.utils.UtilsDB;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
//@Service
public final class CardService {
    private final CardOldRepository repository;
    private final Properties externalProperties = new Properties();
    private static final Logger LOG = getLogger(CardService.class);

    public CardService(CardOldRepository repository) {
        this.repository = repository;
    }

    public List<CardOld> findAll() {
        return null;//repository.findAll();
    }

    public Optional<CardOld> findById(String id) {
        return null;//repository.findById(id);
    }

    public void checkCard() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_VIPS.DB");
        LOG.warn("pathDB_Card={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, new CardHandler(repository));
        } else {
            LOG.error("Files.notExists {}", pathDB);
        }
    }

}
