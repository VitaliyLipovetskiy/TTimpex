package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.repository.CardRepository;
import com.lvv.ttimpex2.service.handlers.CardHandler;
import com.lvv.ttimpex2.utils.UtilsDB;
import org.slf4j.Logger;
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
public class CardService {
    final private CardRepository repository;
    final private Properties externalProperties = new Properties();
    final static private Logger log = getLogger(CardService.class);

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public void checkCard() {
        String path = UtilsDB.pathDB(externalProperties);
        Path pathDB = Paths.get( path + "TRZ_VIPS.DB");
        log.warn("pathDB_Card={}", pathDB);
        if (Files.exists(pathDB)) {
            ParadoxService.tableParadoxHandler(pathDB, new CardHandler(repository));
        } else {
            log.error("Files.notExists {}", pathDB);
        }
    }

}
