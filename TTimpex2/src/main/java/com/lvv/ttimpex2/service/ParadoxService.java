package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.repository.TimeStampRepository;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class ParadoxService {
    final private TimeStampRepository timeStampRepository;
    final static private Logger log = getLogger(TimeStampServiceImpl.class);

    @Autowired
    public ParadoxService(TimeStampRepository timestampRepository) {
        this.timeStampRepository = timestampRepository;
    }

    public void tableParadoxHandler(Path pathDB, ParadoxHandler paradoxHandler) {
        try {
            Class.forName("com.googlecode.paradox.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:paradox:" + pathDB.getParent());
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM " +
                         pathDB.getFileName().toString().replaceAll("\\.\\w+$", ""))){

                paradoxHandler.call(pathDB, resultSet, timeStampRepository);

            } catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            log.error(e.toString());
        }
    }

}
