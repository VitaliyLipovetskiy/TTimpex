package com.lvv.ttimpex2.service.old;

import com.lvv.ttimpex2.repository.old.SCodeOldRepository;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public final class SCodeHandler implements ParadoxHandler {
    private final SCodeOldRepository repository;

    public SCodeHandler(SCodeOldRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Path pathDB, ResultSet resultSet) throws SQLException {
//        repository.deleteAll();


//        while (resultSet.next()) {
//            SCode sCode = new SCode(
//                    resultSet.getString("CARD"),
//                    resultSet.getString("SCODE"));
//            if (!repository.existsById(sCode.getId())) {
//                repository.save(sCode);
//            }
//        }
    }
}
