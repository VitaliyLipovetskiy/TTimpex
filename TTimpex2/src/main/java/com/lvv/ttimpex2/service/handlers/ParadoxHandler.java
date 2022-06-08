package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.repository.TimeStampRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public interface ParadoxHandler<T, V> {
    void call(Path pathDB, ResultSet resultSet, JpaRepository<T, V> repository) throws SQLException;
}
