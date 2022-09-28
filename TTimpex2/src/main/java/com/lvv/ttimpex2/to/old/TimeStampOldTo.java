package com.lvv.ttimpex2.to.old;

import com.lvv.ttimpex2.molel.old.CardOld;
import com.lvv.ttimpex2.molel.old.SCodeOld;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
public final class TimeStampOldTo {
    private final String id;
    private final LocalDateTime dateTime;
    private final int post;
    private final int event;
    private final CardOld cardOld;
    private final SCodeOld sCodeOld;
}
