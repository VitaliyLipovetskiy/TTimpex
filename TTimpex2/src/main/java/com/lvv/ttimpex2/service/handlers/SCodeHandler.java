package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.SCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public class SCodeHandler implements ParadoxHandler<SCode, String>{
    @Override
    public void call(Path pathDB, ResultSet resultSet, JpaRepository<SCode, String> repository) throws SQLException {
        while (resultSet.next()) {
            SCode sCode = new SCode(
                    resultSet.getString("CARD"),
                    resultSet.getString("SCODE"));
            if (repository.findById(sCode.getId()).isPresent()) {
                System.out.println("isPresent " + sCode);
            }
//            if (!repository.existsById(sCode.getId())) {
//                System.out.println(repository.save(sCode));
//            }
        }
    }
}
