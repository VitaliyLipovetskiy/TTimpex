package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.Timestamp;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface TimestampService {
    List<Timestamp> findAll();
    List<Timestamp> findAllByPost(int post);
    List<Timestamp> findAllByCard(String card);
    List<Timestamp> findAllByCardAndEvent(String card, int event);
    Timestamp getFirstByCard(String card);
    Timestamp getTopByCardAndEvent(String card, int event);
}
