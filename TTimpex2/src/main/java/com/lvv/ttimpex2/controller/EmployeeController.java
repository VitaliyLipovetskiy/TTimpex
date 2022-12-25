package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.UpdateEmployeeDto;
import com.lvv.ttimpex2.molel.Employee;
import com.lvv.ttimpex2.molel.Worked;
import com.lvv.ttimpex2.service.DayOffService;
import com.lvv.ttimpex2.service.EmployeeService;
import com.lvv.ttimpex2.service.WorkedService;
import com.lvv.ttimpex2.dto.EmployeeDto;
import com.lvv.ttimpex2.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = EmployeeController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    static final String REST_URL = "/api/employees";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final EmployeeService employeeService;
    private final WorkedService workedService;
    private final DayOffService dayOffService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all Employees",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping
    public ResponseEntity<?> getAllEmployed() {
        log.info("Get all employed");
//        return employeeService.getAllEmployed();
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(employeeService.getAllEmployed());
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get information about employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the employee by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found by id",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@Parameter(description = "Id employee") @PathVariable UUID id) {
        log.info("Get employee by id {}", id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(employeeService.getEmployeeDtoById(id));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable int id) {
//        log.info("delete employee {}", id);
//        employeeRepository.worked(id);
//        employeeRepository.delete(id);
//    }

    @Operation(summary = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find employee",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save employee",
                    content = @Content)})
    @PostMapping
    @Validated
    public ResponseEntity<?> createEmployee(
            HttpServletRequest request,
            @RequestBody @Valid UpdateEmployeeDto employeeDto,
            Errors errors) {
        log.info("Create employee {}", employeeDto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        Employee employee = employeeService.createEmployee(employeeDto);
//        System.out.println(employee);
//        } else {
//            log.info("update {}", employee);
////            assureIdConsistent(meal, id);
//            Assert.notNull(employee, "employee must not be null");
////            checkNotFoundWithId(repository.save(meal, userId), meal.id());
//            employeeService.saveEmployee(employee);
//        }
//        employeeDto.setId(employee.getId());
        Worked worked = convertToWorked(employeeDto);
        worked.setEmployee(employee);
//        System.out.println(worked);
//        log.info("createOrUpdate Worked {}", worked);
        workedService.saveWorked(worked);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return new ResponseEntity<>(
                convertToDto(EmployeeDto.class, employee),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated employee",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find employee",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save employee",
                    content = @Content)})
    @PatchMapping("/{employeeId}")
    @Validated
    public ResponseEntity<?> updateEmployee(
            HttpServletRequest request,
            @RequestBody @Valid UpdateEmployeeDto employeeDto,
            @PathVariable UUID employeeId,Errors errors) {
        log.info("Update employee by id {}", employeeId);
//        System.out.println(employeeDto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        Employee employee = employeeService.updateEmployeeById(employeeId, employeeDto);
//        System.out.println(employee);
//        } else {
//            log.info("update {}", employee);
////            assureIdConsistent(meal, id);
//            Assert.notNull(employee, "employee must not be null");
////            checkNotFoundWithId(repository.save(meal, userId), meal.id());
//            employeeService.saveEmployee(employee);
//        }
//        employeeDto.setId(employee.getId());
        Worked worked = convertToWorked(employeeDto);
        worked.setEmployee(employee);
//        System.out.println(worked);
//        log.info("createOrUpdate Worked {}", worked);
        workedService.saveWorked(worked);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return ResponseEntity.ok(convertToDto(EmployeeDto.class, employee));
    }

    private <T> T convertToDto(Class<T> clazz, Employee employee) {
        return modelMapper.map(employee, clazz);
    }

    private <T> Worked convertToWorked(T dto) {
        return modelMapper.map(dto, Worked.class);
    }
}
