package com.lvv.ttimpex2.utils;

import com.lvv.ttimpex2.molel.*;
import com.lvv.ttimpex2.dto.EmployeeDto;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Util {
    private static final ModelMapper modelMapper = new ModelMapper();

    private Util() {}

//    public static List<TimeStampOldTo> getTos(Collection<TimeStampOld> timeStampsOld) {
//        return null;
//    }

//    private static List<TimeStampOldTo> filterByPredicate(Collection<TimeStampOld> timeStampsOld, CardOld cardOld, SCodeOld sCodeOld, Predicate<TimeStampOld> filter) {
//        return null;
//    }

    public static EmployeeDto getEmployeeDto(Employee employee, Worked worked) {
        EmployeeDto employeeDto = convertToEmployeeDto(EmployeeDto.class, employee);
        if (worked != null) {
            employeeDto.setRecruitment(worked.getRecruitment());
            employeeDto.setDismissal(worked.getDismissal());
        }
        return employeeDto;
    }

    public static List<EmployeeDto> getEmployeesDto(Collection<Employee> employees, List<Worked> workedMap) {
//        System.out.println("getEmployeesTo");
        return filterByPredicate(employees, workedMap, employeeTo -> true);
    }

    public static List<EmployeeDto> getFilteredEmployeeTos(Collection<Employee> employees, List<Worked> workedList) {
        return filterByPredicate(employees, workedList, employeeTo -> employeeTo.getRecruitment() != null);
    }

//    public static Worked createWorked(EmployeeDto empTo) {
//        return new Worked(convertToEmployeeEntity(empTo), empTo.getRecruitment(), empTo.getDismissal());
//    }

    private static List<EmployeeDto> filterByPredicate(Collection<Employee> employees, List<Worked> workedList, Predicate<EmployeeDto> filter) {
//        System.out.println("filterByPredicate");
        List<EmployeeDto> employeesDto = employees.stream()
                .map(employee -> convertToEmployeeDto(EmployeeDto.class, employee)).collect(Collectors.toList());
//        System.out.println(employeesDto);
        employeesDto.forEach(employeeTo -> {
            Optional<Worked> workedOptional = workedList.stream().filter(w -> Objects.equals(w.getEmployee().getId(), employeeTo.getId())).findFirst();
            if (workedOptional.isPresent()) {
                employeeTo.setDismissal(workedOptional.get().getDismissal());
                employeeTo.setRecruitment(workedOptional.get().getRecruitment());
            }
        });
        return employeesDto.stream().filter(filter).collect(Collectors.toList());
    }

//    private static EmployeeDto createEmployeeDto(Employee emp) {
//        return convertToEmployeeDto(EmployeeDto.class, emp);
////        return new EmployeeDto(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getMiddleName(), emp.getDepartment(), emp.getCardId(),
////                null, null, emp.getStartTime(), emp.getEndTime(), emp.getAccountingForHoursWorked());
//    }
//
//    private static TimeStampOldTo createTo(TimeStampOld timeStampOld, CardOld cardOld, SCodeOld sCodeOld) {
//        return new TimeStampOldTo(timeStampOld.getId(), timeStampOld.getDateTime(), timeStampOld.getPost(), timeStampOld.getEvent(), cardOld, sCodeOld);
//    }

    public static <T> T convertToEmployeeDto(Class<T> clazz, Employee employee) {
        return modelMapper.map(employee, clazz);
    }
}
