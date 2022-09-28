package com.lvv.ttimpex2.service.old;

import com.lvv.ttimpex2.repository.old.CardOldRepository;
import com.lvv.ttimpex2.service.handlers.ParadoxHandler;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public final class CardHandler implements ParadoxHandler {
    private final CardOldRepository repository;

    public CardHandler(CardOldRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Path pathDB, ResultSet resultSet) throws SQLException {
//        repository.deleteAll();


//        while (resultSet.next()) {
//            Card card = new Card(
//                    resultSet.getString("CARD"),
//                    resultSet.getString("I"),
//                    resultSet.getString("F"),
//                    resultSet.getString("O"),
//                    resultSet.getString("DOL"));
//            if (!repository.existsById(card.getId())) {
//                repository.save(card);
//            }
//        }
    }
}
