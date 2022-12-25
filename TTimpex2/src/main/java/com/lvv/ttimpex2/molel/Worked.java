package com.lvv.ttimpex2.molel;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "worked", schema = "timestamp")
public class Worked {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(name = "recruitment")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recruitment;
    @Column(name = "dismissal")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dismissal;

    public Worked(Employee employee, LocalDate recruitment, LocalDate dismissal) {
        this(null, employee, recruitment, dismissal);
    }

    public Worked(Employee employee, LocalDate recruitment) {
        this(null, employee, recruitment, null);
    }

    public Worked(LocalDate recruitment, LocalDate dismissal) {
        this(null, null, recruitment, dismissal);
    }

    public Worked(LocalDate recruitment) {
        this(null, null, recruitment, null);
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Worked worked = (Worked) o;
//        return Objects.equals(employee, worked.employee) && Objects.equals(recruitment, worked.recruitment) && Objects.equals(dismissal, worked.dismissal);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(employee, recruitment, dismissal);
//    }


}
