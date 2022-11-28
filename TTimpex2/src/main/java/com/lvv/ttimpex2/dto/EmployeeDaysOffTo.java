package com.lvv.ttimpex2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class EmployeeDaysOffTo {
    private final EmployeeDto employeeDto;
    private final List<DayOffAndWorkedDto> daysOffDto;
}
