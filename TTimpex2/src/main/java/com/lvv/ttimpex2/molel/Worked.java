package com.lvv.ttimpex2.molel;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Vitalii Lypovetskyi
 */
public class Worked {
    private Employee employee;
    private LocalDate recruitment;
    private LocalDate dismissal;

    public Worked() {}

    public Worked(Employee employee, LocalDate recruitment, LocalDate dismissal) {
        this.employee = employee;
        this.recruitment = recruitment;
        this.dismissal = dismissal;
    }

    public Worked(Employee employee, LocalDate recruitment) {
        this.employee = employee;
        this.recruitment = recruitment;
    }

    public Worked(LocalDate recruitment, LocalDate dismissal) {
        this.recruitment = recruitment;
        this.dismissal = dismissal;
    }

    public Worked(LocalDate recruitment) {
        this.recruitment = recruitment;
        this.dismissal = null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(LocalDate recruitment) {
        this.recruitment = recruitment;
    }

    public LocalDate getDismissal() {
        return dismissal;
    }

    public void setDismissal(LocalDate dismissal) {
        this.dismissal = dismissal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worked worked = (Worked) o;
        return Objects.equals(employee, worked.employee) && Objects.equals(recruitment, worked.recruitment) && Objects.equals(dismissal, worked.dismissal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, recruitment, dismissal);
    }

    @Override
    public String toString() {
        return "Worked{" +
                "employee=" + employee +
                ", recruitment=" + recruitment +
                ", dismissal=" + dismissal +
                '}';
    }
}
