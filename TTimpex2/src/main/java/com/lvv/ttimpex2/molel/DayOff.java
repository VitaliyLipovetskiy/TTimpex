package com.lvv.ttimpex2.molel;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "days_off", schema = "timestamp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonDeserialize(using = DayOffDeserializer.class)
public class DayOff implements Serializable {
    @EmbeddedId
    private EmployeeDate employeeDate;
    @Column(name = "day_off")
    private Boolean dayOff;

    public DayOff(Employee employee, LocalDate date) {
        this.employeeDate = new EmployeeDate(employee, date);
        this.dayOff = false;
    }
}
