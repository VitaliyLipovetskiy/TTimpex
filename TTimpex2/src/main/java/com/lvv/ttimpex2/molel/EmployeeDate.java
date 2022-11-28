package com.lvv.ttimpex2.molel;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@ToString
public class EmployeeDate implements Serializable {
    @JoinColumn(name = "employee_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private transient Employee employee;
    @Column(name = "date", nullable = false)
    private LocalDate date;
}
