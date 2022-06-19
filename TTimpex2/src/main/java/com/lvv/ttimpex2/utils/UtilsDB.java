package com.lvv.ttimpex2.utils;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
public class UtilsDB {
    final static private Logger log = getLogger(UtilsDB.class);
    public static String pathDB(Properties externalProperties) {
        String fileProperties =
                System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
        log.warn("fileProperties=" + fileProperties);

        try (FileReader reader = new FileReader(fileProperties)){
            externalProperties.load(reader);
        } catch (Exception e) {
            log.error(e.toString());
        }
        String pathDB = externalProperties.getProperty("app.path-db");
        log.warn("Path DB: " + pathDB);
        return pathDB;
    }
}
