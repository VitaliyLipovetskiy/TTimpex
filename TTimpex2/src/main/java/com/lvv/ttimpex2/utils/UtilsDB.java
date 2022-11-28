package com.lvv.ttimpex2.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;


@Slf4j
public final class UtilsDB {
    private UtilsDB() {}

    public static String pathDB(Properties externalProperties) {
        String fileProperties =
                System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
        log.info("fileProperties={}", fileProperties);

        try (FileReader reader = new FileReader(fileProperties)){
            externalProperties.load(reader);
        } catch (Exception e) {
            log.error(e.toString());
        }
        String pathDB = externalProperties.getProperty("app.path-db");
        log.info("Path DB: {}", pathDB);
        return pathDB;
    }
}
