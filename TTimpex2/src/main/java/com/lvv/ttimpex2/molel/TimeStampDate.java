package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "timestampdate", schema = "timestamp")
public class TimeStampDate {

    private EmployeeDate employeeDate;
    private LocalTime coming;   // приход корректировка
    private LocalTime leaving;  // уход корректировка
    private Integer penalty;       // штраф корректировка

    @Override
    public String toString() {
        return "TimeStampDate{" +
                "employeeDate=" + employeeDate +
                ", coming=" + coming +
                ", leaving=" + leaving +
                ", penalty=" + penalty +
                '}';
    }
}
