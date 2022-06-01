package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "timestamp", schema = "ttimpex")
public class Timestamp {
    @Id
//    @Column
    private String id;
//    @Column
    private int post;
//    @Column
    private int event;
//    @Column
    private String card;
//    @Column
    private LocalTime time;
}
