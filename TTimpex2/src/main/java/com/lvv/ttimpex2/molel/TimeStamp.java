package com.lvv.ttimpex2.molel;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "timestamp", schema = "timestamp")
@ToString
public final class TimeStamp {
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;
    @Column(name = "post")
    private int post;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "event")
    private Events event;

    public TimeStamp(Employee employee, LocalTime time, int post, int event) {
        this.employee = employee;
        this.date = LocalDate.now();
        this.time = time;
        this.post = post;
        this.event = Events.getById(event);
        this.id = employee.getId() + post + event + this.date + time;
    }

    public TimeStamp(Employee employee, LocalDate date, LocalTime time, int post, int event) {
        this.employee = employee;
        this.date = date;
        this.time = time;
        this.post = post;
        this.event = Events.getById(event);
        this.id = employee.getId() + post + event + date + time;
    }

    public TimeStamp(Employee employee,LocalDateTime dateTime, int post, int event) {
        this.employee = employee;
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
        this.post = post;
        this.event = Events.getById(event);
        this.id = employee.getId() + post + event + this.date + this.time;
    }
}
