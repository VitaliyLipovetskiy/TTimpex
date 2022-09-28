package com.lvv.ttimpex2.molel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Embeddable
public class EmployeeDate {
    private Employee employee;
    private LocalDate date;

    @Override
    public String toString() {
        return "EmployeeDate{" +
                "employee=" + employee +
                ", date=" + date +
                '}';
    }
}
