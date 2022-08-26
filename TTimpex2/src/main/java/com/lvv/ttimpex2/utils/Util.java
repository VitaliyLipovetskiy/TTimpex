package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.molel.TimeStamp;
import com.lvv.ttimpex2.to.TimeStampTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Vitalii Lypovetskyi
 */
public class Util {
    public Util() {}

    public static List<TimeStampTo> getTos(Collection<TimeStamp> timeStamps) {
        return null;
    }

    public static List<TimeStampTo> filterByPredicate(Collection<TimeStamp> timeStamps, Card card, SCode sCode, Predicate<TimeStamp> filter) {
        return null;
    }

    private static TimeStampTo createTo(TimeStamp timeStamp, Card card, SCode sCode) {
        return new TimeStampTo(timeStamp.getId(), timeStamp.getDateTime(), timeStamp.getPost(), timeStamp.getEvent(), card, sCode);
    }
}
