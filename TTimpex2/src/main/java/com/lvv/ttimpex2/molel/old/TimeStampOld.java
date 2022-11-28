package com.lvv.ttimpex2.molel.old;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Immutable
//@Table(name = "timestamp", schema = "timestamp")
public final class TimeStampOld {
    //    @Id
    //    @Column
    private String id;
    //    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;
    //    @Column
    @NotNull
    private int post;
    //    @Column
    @NotNull
    @Size(min = 4, max = 4)
    private String card;
    //    @Column
    @NotNull
    private int event;
    //    @ManyToOne(cascade = CascadeType.REFRESH)
    //    @JoinColumn(name = "card", insertable=false, updatable=false, nullable = true)
    //    private Card cardCard;

    //    public TimeStamp(String id, LocalDateTime dateTime, int post, String card, int event) {
    //        this.id = id;
    //        this.dateTime = dateTime;
    //        this.post = post;
    //        this.card = card;
    //        this.event = event;
    //    }

    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }
}

