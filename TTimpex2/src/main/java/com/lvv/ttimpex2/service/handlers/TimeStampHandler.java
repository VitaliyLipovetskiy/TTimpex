package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                            resultSet.getTime("time"),
                    resultSet.getInt("post"),
                    Math.abs(resultSet.getInt("event") - 1),
                    resultSet.getString("card"),
                    resultSet.getTime("time").toLocalTime());
            if (!repository.existsById(timestamp.getId())) {
                repository.save(timestamp);
            }
        }
    }
}
