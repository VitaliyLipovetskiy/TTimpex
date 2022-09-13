package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.repository.TimeStampRepository;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;
import com.lvv.ttimpex2.service.handlers.TimeStampHandler;
import com.lvv.ttimpex2.utils.UtilsDB;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public final class ParadoxService {

//    private final JdbcTemplate jdbcTemplate;
    private final TimeStampRepository timeStampRepository;
    private LocalDate localDate;
    private LocalTime lastExecutionTime;
    private String fileDB;
    @Value("${app.sleep}")
    private volatile Long sleep;
    @Value("${app.night}")
    private volatile Long sleepNight;
    private final Properties externalProperties = new Properties();
    private static final Logger LOG = getLogger(ParadoxService.class);

    public ParadoxService(TimeStampRepository timeStampRepository) {
        this.timeStampRepository = timeStampRepository;
//        checkHandling();
    }

    public void setSleep(Long sleep) {
        this.sleep = sleep;
    }

    public void setSleepNight(Long sleepNight) {
        this.sleepNight = sleepNight;
    }

    private void checkTime() {
        LocalTime startTime = LocalTime.of(Integer.parseInt(externalProperties.getProperty("app.time.start")), 0);
        LocalTime endTime = LocalTime.of(Integer.parseInt(externalProperties.getProperty("app.time.end")), 0);
        if ((lastExecutionTime.isBefore(startTime) || lastExecutionTime.isAfter(endTime)) && !sleep.equals(sleepNight)) {
            sleep = sleepNight; // 15 минут
        }
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
//        timeStampRepository.deleteAll();
        String year = String.valueOf(localDate.getYear()).substring(2);
        String month = String.valueOf(localDate.getMonthValue());
        String day = String.valueOf(localDate.getDayOfMonth());
        fileDB = "D" + (day.length() == 1 ? "0" : "") + day + (month.length() == 1 ? "0" : "") + month + "_" + year;

    }

    public void setLastExecutionTime(LocalTime lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
        if (localDate == null || !localDate.equals(LocalDate.now())) {
            setLocalDate(LocalDate.now());
        }
        checkTime();
//        System.out.println(fileDB + " " + lastExecutionTime);
    }

    public static void tableParadoxHandler(Path pathDB, ParadoxHandler paradoxHandler) {
        try {
            Class.forName("com.googlecode.paradox.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:paradox:" + pathDB.getParent());
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM " +
                         pathDB.getFileName().toString().replaceAll("\\.\\w+$", ""))){

                paradoxHandler.call(pathDB, resultSet);

            } catch (Exception e) {
                LOG.error(e.toString());
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    private void checkHandling() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                while (true) {
                    String path = UtilsDB.pathDB(externalProperties);
                    if (externalProperties.getProperty("app.sleep") != null) {
                        sleep = Long.parseLong(externalProperties.getProperty("app.sleep"));
                    }
                    if (externalProperties.getProperty("app.night") != null) {
                        sleepNight = Long.parseLong(externalProperties.getProperty("app.night"));
                    }
                    setLastExecutionTime(LocalTime.now());
                    Path pathDB = Paths.get( path + fileDB + ".DB");
                    LOG.warn("pathDB={}", pathDB);
                    if (Files.exists(pathDB)) {
                        tableParadoxHandler(pathDB, new TimeStampHandler(timeStampRepository));
                    } else {
                        LOG.error("Files.notExists {}", pathDB);
                    }
                    LOG.warn("sleep={} DateTime={} {} fileDB={} count={}",
                            sleep, localDate, lastExecutionTime, fileDB, timeStampRepository.count());
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        LOG.error(e.toString());
                        e.printStackTrace();
                    }
                }
//            }
        });
//        .start();
        executorService.shutdown();
    }

}
