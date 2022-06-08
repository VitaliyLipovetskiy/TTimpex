package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public class CardHandler implements ParadoxHandler<Card, String>{
    @Override
    public void call(Path pathDB, ResultSet resultSet, JpaRepository<Card, String> repository) throws SQLException {
        while (resultSet.next()) {
            Card card = new Card(
                    resultSet.getString("CARD"),
                    resultSet.getString("I"),
                    resultSet.getString("F"),
                    resultSet.getString("O"),
                    resultSet.getString("DOL"));
            System.out.println(card);
//            if (!timeStampRepository.existsById(timestamp.getId())) {
//                timeStampRepository.save(timestamp);
//            }
        }
    }
}
