package com.lvv.ttimpex2.to;

import com.lvv.ttimpex2.molel.Department;

import java.time.LocalTime;

/**
 * @author Vitalii Lypovetskyi
 */
public class EmployeeTo {
    private final int id;
    private final String name;
    private final String department;
    private final String cardId;
    private final Boolean worked;
    private final LocalTime startTime; // согласованное время начала раб дня
    private final LocalTime endTime; // согласованное время окончания раб дня

    public EmployeeTo(int id, String name, String department, String cardId, Boolean worked, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.cardId = cardId;
        this.worked = worked;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EmployeeTo(int id, String name, String department, String cardId) {
        this(id, name, department, cardId, true, null, null);
    }

    public EmployeeTo(int id, String name) {
        this(id, name, null, null, true, null, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getDepartment() {
        return department;
    }

    public Boolean getWorked() {
        return worked;
    }
}
