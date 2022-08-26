package com.lvv.ttimpex2.to;

import com.lvv.ttimpex2.molel.Card;
import com.lvv.ttimpex2.molel.SCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
public final class TimeStampTo {
    private final String id;
    private final LocalDateTime dateTime;
    private final int post;
    private final int event;
    private final Card card;
    private final SCode sCode;
}
