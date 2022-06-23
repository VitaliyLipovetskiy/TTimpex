package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public final class SCodeHandler implements ParadoxHandler {
    private final SCodeRepository repository;

    public SCodeHandler(SCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Path pathDB, ResultSet resultSet) throws SQLException {
        repository.deleteAll();
        while (resultSet.next()) {
            SCode sCode = new SCode(
                    resultSet.getString("CARD"),
                    resultSet.getString("SCODE"));
            if (!repository.existsById(sCode.getId())) {
                repository.save(sCode);
            }
        }
    }
}
