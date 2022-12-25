package com.lvv.ttimpex2.service.handlers;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public interface ParadoxHandler {
    void call(Path pathDB, ResultSet resultSet, LocalDate date) throws SQLException;
}
