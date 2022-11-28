package com.lvv.ttimpex2.dto.old;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public final class TimeStampOldTo {
    private final String id;
    private final LocalDateTime dateTime;
    private final int post;
    private final int event;
//    private final CardOld cardOld;
//    private final SCodeOld sCodeOld;
}
