package com.lvv.ttimpex2.molel;

import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
public interface TimestampProjection {
    int getPost();
    int getEvent();
    String getCard();
    LocalTime getTime();
}
