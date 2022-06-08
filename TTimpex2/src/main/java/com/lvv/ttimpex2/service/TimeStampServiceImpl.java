package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.repository.TimeStampRepository;
import com.lvv.ttimpex2.service.handlers.CardHandler;
import com.lvv.ttimpex2.service.handlers.SCodeHandler;
import com.lvv.ttimpex2.service.handlers.TimeStampHandler;
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
public class TimeStampServiceImpl implements TimeStampService {
    final private TimeStampRepository timeStampRepository;
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
    final static private Logger log = getLogger(TimeStampServiceImpl.class);

    @Autowired
    public TimeStampServiceImpl(TimeStampRepository timestampRepository, ParadoxService paradoxService, Environment env) {
        this.timeStampRepository = timestampRepository;
        this.paradoxService = paradoxService;
        this.env = env;
        checkHandling();
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        timeStampRepository.deleteAll();
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

    @Override
    public List<TimeStamp> findAll() {
        return timeStampRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
    }

    @Override
    public List<TimeStamp> findAllByPost(int post) {
        return timeStampRepository.findAllByPost(post);
    }

    @Override
    public List<TimeStamp> findAllByCard(String card) {
        return timeStampRepository.findAllByCard(card);
    }

    @Override
    public List<TimeStamp> findAllByCardAndEvent(String card, int event) {
        return timeStampRepository.findAllByCardAndEvent(card, event);
    }

    @Override
    public TimeStamp getFirstByCard(String card) {
        return timeStampRepository.getFirstByCardOrderByTimeDesc(card);
    }

    @Override
    public TimeStamp getTopByCardAndEvent(String card, int event) {
        return timeStampRepository.getTopByCardAndEventOrderByTimeDesc(card, event);
    }

    private void checkTime() {
        LocalTime startTime = LocalTime.of(Integer.parseInt(externalProperties.getProperty("app.time.start")), 0);
        LocalTime endTime = LocalTime.of(Integer.parseInt(externalProperties.getProperty("app.time.end")), 0);
        if ((lastExecutionTime.isAfter(startTime) || lastExecutionTime.isBefore(endTime)) && !sleep.equals(sleepNight)) {
            sleep = sleepNight; // 15 минут
        }
    }

    private void checkHandling() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String fileProperties =
                            System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
                    log.warn("fileProperties=" + fileProperties);

                    try (FileReader reader = new FileReader(fileProperties)){
                        externalProperties.load(reader);
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                    setLastExecutionTime(LocalTime.now());
                    sleep = Long.parseLong(externalProperties.getProperty("app.sleep"));
                    sleepNight = Long.parseLong(externalProperties.getProperty("app.night"));
                    String path = externalProperties.getProperty("app.path-db");
                    if (path == null) {
                        path = env.getProperty("app.path-db");
                    }
                    Path pathDB = Paths.get( path + fileDB + ".DB");
                    log.warn("pathDB=" + pathDB);

                    if (Files.notExists(pathDB)) {
                        log.error("Files.notExists " + pathDB);
                        return;
                    }
                    paradoxService.tableParadoxHandler(pathDB, new TimeStampHandler());

//                    Path pathCard = Paths.get(path + "TRZ_VIPS.DB");
//                    paradoxService.tableParadoxHandler(pathCard, new CardHandler());

                    Path pathSCode = Paths.get(path + "TRZ_SC.DB");
                    paradoxService.tableParadoxHandler(pathSCode, new SCodeHandler());

                    log.warn("sleep=" + sleep +
                            " DateTime=" + localDate + " " + lastExecutionTime +
                            " fileDB=" + fileDB +
                            " count=" + timeStampRepository.count());
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
