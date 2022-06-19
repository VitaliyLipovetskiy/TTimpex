package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.SCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public class SCodeHandler implements ParadoxHandler {
    final private SCodeRepository repository;

    @Autowired
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
                System.out.println(repository.save(sCode));
            }
        }
    }
}
