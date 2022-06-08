package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.repository.TimeStampRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public class SCodeHandler implements ParadoxHandler{
    @Override
    public void call(Path pathDB, ResultSet resultSet, TimeStampRepository timeStampRepository) throws SQLException {
        while (resultSet.next()) {
            SCode sCode = new SCode(
                    resultSet.getString("CARD"),
                    resultSet.getString("SCODE"));
            System.out.println(sCode);
//            if (!timeStampRepository.existsById(timestamp.getId())) {
//                timeStampRepository.save(timestamp);
//            }
        }
    }
}
