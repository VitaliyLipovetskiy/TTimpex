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
import java.util.Optional;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public final class CardService {
    private final CardRepository repository;
    private final Properties externalProperties = new Properties();
    private static final Logger LOG = getLogger(CardService.class);

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Optional<Card> findById(String id) {
        return repository.findById(id);
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
