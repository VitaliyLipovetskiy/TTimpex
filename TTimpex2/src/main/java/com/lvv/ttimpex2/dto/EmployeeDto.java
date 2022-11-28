package com.lvv.ttimpex2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
//    @JsonDeserialize(using = DepartmentDeserializer.class)
    private DepartmentDto department;
    @JsonManagedReference
    private List<ShortSCodeDto> cards;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recruitment;     // Прием
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dismissal;       // Увольнение
    @DateTimeFormat(pattern = "HH:mm", iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime; // согласованное время начала раб дня
    @DateTimeFormat(pattern = "HH:mm", iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;   // согласованное время окончания раб дня
    private Boolean accountingForHoursWorked;

    public String getFullName() {
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
        return stringBuilder.toString().trim();
    }

    @JsonProperty("departmentName")
    public String getDepartmentName() {
        if (department == null) {
            return "";
        }
        return department.getName();
    }
}
