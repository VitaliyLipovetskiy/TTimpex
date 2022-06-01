package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Timestamp;
import com.lvv.ttimpex2.molel.TimestampProjection;
import com.lvv.ttimpex2.repository.TimestampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    volatile private Long sleep;

    @Value("${dev}")
    private boolean dev;

    @Autowired
    public TimestampServiceImpl(TimestampRepository timestampRepository, Environment env) {
        this.timestampRepository = timestampRepository;
        this.env = env;
        this.sleep = 1000L;
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
        if (localDate == null || !localDate.equals(LocalDate.now())) {
            localDate = LocalDate.now();
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
                    lastExecutionTime = LocalTime.now();
                    setLocalDate();
                    System.out.println(fileDB + " " + lastExecutionTime);
                    System.out.println("handlingParadox => " + handlingParadox(fileDB));
//                    System.out.println(timestampRepository.count());
                    try {
//                        System.out.println(sleep);
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public List<Timestamp> handlingParadox(String fileName) {
        Properties properties = new Properties();
        String CONNECTION_STRING = null;
        String fileProperties = dev ? "/Users/vitaliy/IdeaProjects/db" : System.getProperty("user.dir");
        System.out.println("fileProperties=" + fileProperties + "/db.properties");
        System.out.println("System.getProperty(\"user.dir\")=" + System.getProperty("user.dir"));
        try (FileReader reader = new FileReader(fileProperties + "/db.properties")){
            properties.load(reader);
            CONNECTION_STRING = properties.getProperty("patch-db");
            sleep = Long.parseLong(properties.getProperty("sleep"));
            System.out.println("sleep = " + sleep);
        } catch (Exception e) {}
        CONNECTION_STRING = "jdbc:paradox:" + (CONNECTION_STRING == null ? env.getProperty("patch-db") : CONNECTION_STRING);
        System.out.println("CONNECTION_STRING=" + CONNECTION_STRING);
        System.out.println("env.getProperty(\"patch-db\")=" + env.getProperty("patch-db"));
        try {
            Class.forName("com.googlecode.paradox.Driver");
            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + fileName)){

                List<Timestamp> result = new ArrayList<>();
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
//                        System.out.println(timestampRepository.count());
                        timestampRepository.save(timestamp);
                    }
                    result.add(timestamp);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

}
