package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
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

}
