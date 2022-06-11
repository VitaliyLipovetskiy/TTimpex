package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
public class TimeStampHandler implements ParadoxHandler<TimeStamp, String> {
    @Override
    public void call(Path pathDB, ResultSet resultSet, JpaRepository<TimeStamp, String> repository) throws SQLException {
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
                System.out.println(repository.save(timestamp));
            }
        }
    }
}
