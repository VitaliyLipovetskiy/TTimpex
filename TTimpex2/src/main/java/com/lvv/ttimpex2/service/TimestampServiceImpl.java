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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
public class TimestampServiceImpl implements TimestampService {
    final private TimestampRepository timestampRepository;
    final private ParadoxService paradoxService;
    private LocalDate localDate;
    private LocalTime lastExecutionTime;
    private String fileDB;
    @Value("${app.sleep}")
    volatile private Long sleep;
    @Value("${app.night}")
    volatile private  Long sleepNight;
    final private Environment env;
    final private Properties externalProperties = new Properties();
    final static private Logger log = getLogger(TimestampServiceImpl.class);

    @Autowired
    public TimestampServiceImpl(TimestampRepository timestampRepository, ParadoxService paradoxService, Environment env) {
        this.timestampRepository = timestampRepository;
        this.paradoxService = paradoxService;
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

    private void checkLocalDate() {
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

    private void checkHandling() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    checkLocalDate();
//                    System.out.println(fileDB + " " + lastExecutionTime);
                    if ((lastExecutionTime.isAfter(LocalTime.of(20, 0)) ||
                            lastExecutionTime.isBefore(LocalTime.of(7, 0))) &&
                            !sleep.equals(sleepNight)
                    ) {
                        sleep = sleepNight; // 15 минут
//                        System.out.println(sleep);
                    }
                    String fileProperties =
                            System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
//                    System.out.println("====");
//                    System.getProperties().stringPropertyNames().forEach(p -> System.out.println(p + "=>" + System.getProperty(p)));
//                    System.out.println("====");
                    log.debug("fileProperties=" + fileProperties);

                    Path pathDB = null;
                    try (FileReader reader = new FileReader(fileProperties)){
                        externalProperties.load(reader);
                        pathDB = Paths.get(externalProperties.getProperty("app.path-db") + fileDB + ".DB");
                        sleep = Long.parseLong(externalProperties.getProperty("app.sleep"));
                        sleepNight = Long.parseLong(externalProperties.getProperty("app.night"));
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                    if (pathDB == null) {
                        pathDB = Paths.get(env.getProperty("app.path-db") + fileDB + ".DB");
                    }
                    log.debug("pathDB=" + pathDB);

                    if (Files.notExists(pathDB)) {
                        log.debug("Files.notExists " + pathDB);
                        return;
                    }
                    paradoxService.handlingParadox(pathDB);

                    log.debug("sleep=" + sleep +
                            " DateTime=" + localDate + " " + lastExecutionTime +
                            " fileDB=" + fileDB +
                            " count=" + timestampRepository.count());
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
}
