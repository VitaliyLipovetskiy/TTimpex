package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timestamp", schema = "timestamp")
public class TimeStamp {
    @Id
    @Column
    private String id;
    @Column(name = "time")
    private LocalDateTime dateTime;
    @Column
    private int post;
    @Column
    private String card;
    @Column
    private int event;

    private LocalDate localDate;

    public TimeStamp(String id, LocalDateTime dateTime, int post, String card, int event) {
        this.id = id;
        this.dateTime = dateTime;
        this.post = post;
        this.card = card;
        this.event = event;
    }

    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }
}
