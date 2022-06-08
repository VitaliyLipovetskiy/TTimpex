package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Timestamp;
import com.lvv.ttimpex2.repository.TimestampRepository;
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
    final private TimestampRepository timestampRepository;
    final static private Logger log = getLogger(TimestampServiceImpl.class);

    @Autowired
    public ParadoxService(TimestampRepository timestampRepository) {
        this.timestampRepository = timestampRepository;
    }

    public void handlingParadox(Path pathDB) {
        try {
            Class.forName("com.googlecode.paradox.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:paradox:" + pathDB.getParent());
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM " +
                         pathDB.getFileName().toString().replaceAll("\\.\\w+$", ""))){
                while (resultSet.next()) {
                    Timestamp timestamp = new Timestamp(
                            resultSet.getString("card") +
                                    resultSet.getString("post") +
                                    resultSet.getString("event") +
                                    resultSet.getTime("time"),
                            resultSet.getInt("post"),
                            Math.abs(resultSet.getInt("event") - 1),
                            resultSet.getString("card"),
                            resultSet.getTime("time").toLocalTime());
                    if (!timestampRepository.existsById(timestamp.getId())) {
                        timestampRepository.save(timestamp);
                    }
                }
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
