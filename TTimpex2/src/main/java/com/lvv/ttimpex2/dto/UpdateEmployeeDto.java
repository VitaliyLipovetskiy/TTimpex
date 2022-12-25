package com.lvv.ttimpex2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lvv.ttimpex2.validation.NoHtml;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateEmployeeDto {
    @NotBlank(message = "Employee first name can't be blank")
    @NotNull(message = "Employee first name can't be null")
    @NoHtml
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;
    @NotBlank(message = "Employee last name can't be blank")
    @NotNull(message = "Employee last name can't be null")
    @NoHtml
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;
    @NoHtml
    @Size(max = 50, message = "Middle name must be more than 50 characters.")
    private String middleName;
    @Null
    private UUID departmentId;
    @Null
    @Size(min = 4, max = 4, message = "Card id must be more than 4 characters.")
    private String cardId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recruitment;     // Прием
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dismissal;       // Увольнение
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime; // согласованное время начала раб дня
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;   // согласованное время окончания раб дня
    @NotNull
    private Boolean accountingForHoursWorked;

}
