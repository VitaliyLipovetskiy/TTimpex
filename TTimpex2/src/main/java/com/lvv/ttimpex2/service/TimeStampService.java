package com.lvv.ttimpex2.service;

import com.lvv.ttimpex2.molel.TimeStamp;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public interface TimeStampService {
    List<TimeStamp> findAll();
    List<TimeStamp> findAllByPost(int post);
    List<TimeStamp> findAllByCard(String card);
    List<TimeStamp> findAllByCardAndEvent(String card, int event);
    TimeStamp getFirstByCard(String card);
    TimeStamp getTopByCardAndEvent(String card, int event);
}
