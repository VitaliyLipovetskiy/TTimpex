package com.lvv.ttimpex2.molel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "timestampdate", schema = "timestamp")
public class TimeStampDate {
    @EmbeddedId
    private EmployeeDate employeeDate;
    @Column(name = "coming")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime coming;   // приход корректировка
    @Column(name = "leaving")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime leaving;  // уход корректировка
    @Column(name = "penalty")
    @Positive
    private Integer penalty;       // штраф корректировка
}
