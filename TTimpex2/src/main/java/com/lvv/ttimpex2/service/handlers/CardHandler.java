package com.lvv.ttimpex2.service.handlers;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.repository.CardRepository;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Lypovetskyi
 */
public class CardHandler implements ParadoxHandler{
    final private CardRepository repository;

    public CardHandler(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Path pathDB, ResultSet resultSet) throws SQLException {
        repository.deleteAll();
        while (resultSet.next()) {
            Card card = new Card(
                    resultSet.getString("CARD"),
                    resultSet.getString("I"),
                    resultSet.getString("F"),
                    resultSet.getString("O"),
                    resultSet.getString("DOL"));
            if (!repository.existsById(card.getId())) {
                System.out.println(repository.save(card));
            }
        }
    }
}
