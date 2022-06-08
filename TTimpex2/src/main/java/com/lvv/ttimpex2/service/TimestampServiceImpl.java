package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Timestamp;
import com.lvv.ttimpex2.repository.TimestampRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class TimestampServiceImpl implements TimestampService {
    private Environment env;
    private TimestampRepository timestampRepository;
    private LocalDate localDate;
    private LocalTime lastExecutionTime;
    private String fileDB;
    @Value("${app.sleep}")
    volatile private Long sleep;
    @Value("${app.night}")
    volatile private  Long sleepNight;

    final private Properties externalProperties = new Properties();

    private static final Logger log = getLogger(TimestampServiceImpl.class);

    @Autowired
    public TimestampServiceImpl(TimestampRepository timestampRepository, Environment env) {
        this.timestampRepository = timestampRepository;
        this.env = env;
        checkHandling();
    }

    @Override
    public List<Timestamp> findAll() {
        return timestampRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
    }

    @Override
    public List<Timestamp> findAllByPost(int post) {
        return timestampRepository.findAllByPost(post);
    }

    @Override
    public List<Timestamp> findAllByCard(String card) {
        return timestampRepository.findAllByCard(card);
    }

    @Override
    public List<Timestamp> findAllByCardAndEvent(String card, int event) {
        return timestampRepository.findAllByCardAndEvent(card, event);
    }

    @Override
    public Timestamp getFirstByCard(String card) {
        return timestampRepository.getFirstByCardOrderByTimeDesc(card);
    }

    @Override
    public Timestamp getTopByCardAndEvent(String card, int event) {
        return timestampRepository.getTopByCardAndEventOrderByTimeDesc(card, event);
    }

    public void setLocalDate() {
        lastExecutionTime = LocalTime.now();
        if (localDate == null || !localDate.equals(LocalDate.now())) {
            localDate = LocalDate.now();
            timestampRepository.deleteAll();
            String year = String.valueOf(localDate.getYear()).substring(2);
            String month = String.valueOf(localDate.getMonthValue());
            String day = String.valueOf(localDate.getDayOfMonth());
            fileDB = "D" + (day.length() == 1 ? "0" : "") + day + (month.length() == 1 ? "0" : "") + month + "_" + year;
        }
    }

    public void checkHandling() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    setLocalDate();
//                    System.out.println(fileDB + " " + lastExecutionTime);
                    if ((lastExecutionTime.isAfter(LocalTime.of(20, 0)) ||
                            lastExecutionTime.isBefore(LocalTime.of(7, 0))) &&
                            !sleep.equals(sleepNight)
                    ) {
                        sleep = sleepNight; // 15 минут
//                        System.out.println(sleep);
                    }
                    handlingParadox(fileDB);

                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        log.error(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void handlingParadox(String fileName) {
        String fileProperties =
                System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
//        System.out.println("====");
//        System.getProperties().stringPropertyNames().forEach(p -> System.out.println(p + "=>" + System.getProperty(p)));
//        System.out.println("====");
        log.debug("fileProperties=" + fileProperties);

        String pathDB = null;
        try (FileReader reader = new FileReader(fileProperties)){
            externalProperties.load(reader);
            pathDB = externalProperties.getProperty("app.path-db");
//            System.out.println("pathDB=" + pathDB);
            sleep = Long.parseLong(externalProperties.getProperty("app.sleep"));
//            System.out.println("sleep = " + sleep);
            sleepNight = Long.parseLong(externalProperties.getProperty("app.night"));
//            System.out.println("night = " + sleepNight);
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (pathDB == null) {
            pathDB = env.getProperty("app.path-db");
        }
        log.debug("pathDB=" + pathDB + fileName + ".DB");

        if (Files.notExists(Paths.get(pathDB + fileName + ".DB"))) {
            log.debug("Files.notExists " + pathDB + fileName + ".DB");
            return;
        }
        try {
            Class.forName("com.googlecode.paradox.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:paradox:" + pathDB);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + fileName)){

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
        log.debug("sleep=" + sleep +
                " DateTime=" + localDate + " " + lastExecutionTime +
                " fileDB=" + fileDB +
                " count=" + timestampRepository.count());
    }

}
