package com.lvv.ttimpex2.utils;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
public final class UtilsDB {
    private static final Logger LOG = getLogger(UtilsDB.class);
    private UtilsDB() {}

    public static String pathDB(Properties externalProperties) {
        String fileProperties =
                System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
        LOG.warn("fileProperties={}", fileProperties);

        try (FileReader reader = new FileReader(fileProperties)){
            externalProperties.load(reader);
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        String pathDB = externalProperties.getProperty("app.path-db");
        LOG.warn("Path DB: {}", pathDB);
        return pathDB;
    }
}
