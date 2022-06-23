package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.repository.TimeStampRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
public final class TimeStampHandler implements ParadoxHandler {
    private final TimeStampRepository repository;

    public TimeStampHandler(TimeStampRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Path pathDB, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            TimeStamp timestamp = new TimeStamp(
                    resultSet.getString("card") +
                            resultSet.getString("post") +
                            resultSet.getString("event") +
                            LocalDateTime.of(LocalDate.now(), resultSet.getTime("time").toLocalTime()),
                    LocalDateTime.of(LocalDate.now(), resultSet.getTime("time").toLocalTime()),
                    resultSet.getInt("post"),
                    resultSet.getString("card"),
                    Math.abs(resultSet.getInt("event") - 1));

            if (!repository.existsById(timestamp.getId())) {
                repository.save(timestamp);
            }
        }
    }
}
