package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.repository.TimeStampRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public interface ParadoxHandler {
    void call(Path pathDB, ResultSet resultSet, TimeStampRepository timeStampRepository) throws SQLException;
}
