package com.lvv.ttimpex2.molel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "settings", schema = "timestamp")
public class Setting {
    @Id
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
//    @Column(length = 36, nullable = false, updatable = false)
//    private String id;
    @Column(name = "date", unique = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @Column(name = "start_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime; // время начала раб дня по умолчанию
    @Column(name = "end_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime; // время окончания раб дня по умолчанию
    @Column(name = "penalty_being_late")
    private Integer penaltyBeingLate; // штраф за опоздание за 1 час
    @Column(name = "penalty_early_care")
    private Integer penaltyEarlyCare; // штраф за ранний уход за 1 час
    @Column(name = "penalty_for_missing_timestamp")
    private Integer penaltyForMissingTimestamp; // штраф за отсутствие отметки
    @Column(name = "penalty_absenteeism")
    private Integer penaltyAbsenteeism; // штраф за прогул

//    public Setting(LocalDate date, LocalTime startTime, LocalTime endTime, Integer penaltyBeingLate, Integer penaltyEarlyCare, Integer penaltyForMissingTimestamp, Integer penaltyAbsenteeism) {
//        this(null, date, startTime, endTime, penaltyBeingLate, penaltyEarlyCare, penaltyForMissingTimestamp, penaltyAbsenteeism);
//    }
}
