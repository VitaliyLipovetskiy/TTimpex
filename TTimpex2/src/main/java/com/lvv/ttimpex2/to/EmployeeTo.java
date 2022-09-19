package com.lvv.ttimpex2.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.utils.json.DepartmentDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
public class EmployeeTo {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String name;
    @JsonDeserialize(using = DepartmentDeserializer.class)
    private Department department;
    private String cardId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate recruitment;     // Прием
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dismissal;       // Увольнение
    private LocalTime startTime; // согласованное время начала раб дня
    private LocalTime endTime;   // согласованное время окончания раб дня

    public EmployeeTo() {}

    public EmployeeTo(int id, String firstName, String lastName, String middleName, Department department, String cardId, LocalDate recruitment, LocalDate dismissal, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        setName();
        this.department = department;
        this.cardId = cardId;
        this.recruitment = recruitment;
        this.dismissal = dismissal;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EmployeeTo(int id, Department department, String cardId, String firstName, String lastName, String middleName) {
        this(id, firstName, lastName, middleName, department, cardId, null, null, null, null);
    }

    public EmployeeTo(int id, String firstName, String lastName, String middleName) {
        this(id, firstName, lastName, middleName, null, null, null, null, null, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        StringBuilder stringBuilder = new StringBuilder();
        if (lastName != null) {
            stringBuilder.append(lastName);
        }
        if (firstName != null) {
            stringBuilder.append(" ").append(firstName);
        }
        if (middleName != null) {
            stringBuilder.append(" ").append(middleName);
        }
        this.name = stringBuilder.toString().trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getCardId() {
        return cardId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @JsonProperty("departmentName")
    public String getDepartmentName() {
        if (department == null) {
            return "";
        }
        return department.getName();
    }

    public Department getDepartment() {
        return department;
    }

    public LocalDate getRecruitment() {
        return recruitment;
    }

    public LocalDate getDismissal() {
        return dismissal;
    }

    public void setRecruitment(LocalDate recruitment) {
        this.recruitment = recruitment;
    }

    public void setDismissal(LocalDate dismissal) {
        this.dismissal = dismissal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "EmployeeTo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", cardId='" + cardId + '\'' +
                ", recruitment=" + recruitment +
                ", dismissal=" + dismissal +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
