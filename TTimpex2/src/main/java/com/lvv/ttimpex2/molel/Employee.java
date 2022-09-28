package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvv.ttimpex2.to.EmployeeTo;
import com.lvv.ttimpex2.utils.json.DepartmentDeserializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
@Data
//@Entity
public class Employee extends AbstractBaseEntity {
    private String firstName;
    private String lastName;
    private String middleName;
    @JsonDeserialize(using = DepartmentDeserializer.class)
    private Department department;
    private String cardId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime; // согласованное время начала раб дня
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime; // согласованное время окончания раб дня
    private Boolean accountingForHoursWorked;

    public Employee() {
        super();
    }

    public Employee(Integer id, String firstName, String lastName, String middleName, Department department,
                    String cardId, LocalTime startTime, LocalTime endTime, Boolean accountingForHoursWorked) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.department = department;
        this.cardId = cardId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.accountingForHoursWorked = accountingForHoursWorked;
    }

    public Employee(Integer id, String name, Department department, String cardId, LocalTime startTime, LocalTime endTime) {
        this(null, null, null, null, department, cardId, startTime, endTime, false);
        setNames(name);
    }

    public Employee(String name, Department department, String cardId, LocalTime startTime, LocalTime endTime, Boolean accountingForHoursWorked) {
        this(null, null, department, cardId, startTime, endTime);
        setNames(name);
        setAccountingForHoursWorked(accountingForHoursWorked);
    }

    public Employee(String name, Department department, String cardId, LocalTime startTime, LocalTime endTime) {
        this(null, null, department, cardId, startTime, endTime);
        setNames(name);
    }

    public Employee(Integer id, String name, Department department, String cardId) {
        this(id, name, department, cardId, null, null);
    }

    public Employee(String name, Department department, String cardId) {
        this(name, department, cardId, null, null);
    }

    public Employee(String name, Department department, String cardId, Boolean accountingForHoursWorked) {
        this(name, department, cardId, null, null, accountingForHoursWorked);
    }

    public Employee(Integer id, String name) {
        this(id, name, null, null, null, null);
    }

    public Employee(String name) {
        this(null, name);
    }

    public Employee(Integer id, String name, Department department) {
        this(id, name, department, null, null, null);
    }

    public Employee(Integer id, String name, String cardId) {
        this(id, name, null, cardId, null, null);
    }

    public Employee(EmployeeTo empTo) {
        this(empTo.getId(), empTo.getFirstName(), empTo.getLastName(), empTo.getMiddleName(),
                empTo.getDepartment(), empTo.getCardId(), empTo.getStartTime(), empTo.getEndTime(), empTo.getAccountingForHoursWorked());
    }
    public void setNames(String name) {
        if (name == null) return;
        String[] names = name.split(" ");
        if (names.length > 0) {
            this.lastName = names[0];
        }
        if (names.length > 1) {
            this.firstName = names[1];
        }
        if (names.length > 2) {
            this.middleName = names[2];
        }
    }

    public String getNameDepartment() {
        return department == null ? null : department.getName();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", department=" + department +
                ", cardId='" + cardId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                '}';
    }
}
