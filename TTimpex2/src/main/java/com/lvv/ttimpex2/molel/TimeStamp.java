package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Immutable
//@Table(name = "timestamp", schema = "timestamp")
public final class TimeStamp {
    private String id;
    private Employee employee;
    private LocalDate date;
    private LocalTime time;
    private int post;
    private int event;

    public TimeStamp(Employee employee, LocalTime time, int post, int event) {
        this.employee = employee;
        this.date = LocalDate.now();
        this.time = time;
        this.post = post;
        this.event = event;
        this.id = "" + employee.getCardId() + post + event + this.date + time;
    }

    public TimeStamp(Employee employee, LocalDateTime dateTime, int post, int event) {
        this.employee = employee;
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
        this.post = post;
        this.event = event;
        this.id = "" + employee.getCardId() + post + event + this.date + this.time;
    }
}
