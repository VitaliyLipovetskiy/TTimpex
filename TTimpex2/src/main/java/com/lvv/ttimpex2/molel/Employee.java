package com.lvv.ttimpex2.molel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvv.ttimpex2.utils.json.DepartmentDeserializer;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
public class Employee extends AbstractBaseEntity implements HasId {
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

    public Employee() {
        super();
    }

    public Employee(Integer id, String firstName, String lastName, String middleName, Department department, String cardId, LocalTime startTime, LocalTime endTime) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.department = department;
        this.cardId = cardId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Employee(Integer id, String name, Department department, String cardId, LocalTime startTime, LocalTime endTime) {
        this(null, null, null, null, department, cardId, startTime, endTime);
        setNames(name);
    }

    public Employee(String name, Department department, String cardId, LocalTime startTime, LocalTime endTime) {
        this(null, null, department, cardId, startTime, endTime);
        setNames(name);
    }

    public Employee(Integer id, String name, Department department, String cardId) {
        this(id, name, department, cardId, null, null);
    }

    public Employee(String name, Department department, String cardId) {
        this(null, name, department, cardId, null, null);
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public Department getDepartment() {
        return department;
    }

    public String getNameDepartment() {
        return department == null ? null : department.getName();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
