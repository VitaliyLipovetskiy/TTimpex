package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.UpdateDepartmentDto;
import com.lvv.ttimpex2.dto.DepartmentDto;
import com.lvv.ttimpex2.molel.Department;
import com.lvv.ttimpex2.service.DepartmentService;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = DepartmentController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class DepartmentController {
    static final String REST_URL = "/api/departments";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Get all departments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all Departments",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartmentDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        log.info("getAllDepartments");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
            List<DepartmentDto> departmentsDto = departmentService.getAllDepartments()
                    .stream().map(department -> convertToDto(DepartmentDto.class, department)).collect(Collectors.toList());
            return ResponseEntity.ok(departmentsDto);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get information about department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the department by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found by id",
                    content = @Content)})
    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getDepartmentById(@Parameter(description = "Id Department") @PathVariable UUID departmentId) {
        log.info("Get department by id={}", departmentId);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
            return ResponseEntity.ok(convertToDto(DepartmentDto.class, departmentService.getDepartmentById(departmentId)));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Create new department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find department",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save department",
                    content = @Content)})
    @PostMapping
    @Validated
    public ResponseEntity<?> createDepartment(
            HttpServletRequest request,
            @Valid @RequestBody UpdateDepartmentDto updateDepartmentDto,
            Errors errors) {
        log.info("create App {}", updateDepartmentDto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        Department department = convertToEntity(updateDepartmentDto);
        return new ResponseEntity<>(convertToDto(DepartmentDto.class, departmentService.createDepartment(department)), HttpStatus.CREATED);
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Delete department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the department by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content) })
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartmentById(@Parameter(description = "Id Department") @PathVariable UUID departmentId) {
        log.info("delete department by id {}", departmentId);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
            return  ResponseEntity.ok(convertToDto(DepartmentDto.class, departmentService.deleteDepartmentById(departmentId)));
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Update department by id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated department by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save department",
                    content = @Content) })
    @PatchMapping("/{departmentId}")
    @Validated
    public ResponseEntity<?> updateDepartment(
            HttpServletRequest request,
            @RequestBody @Valid UpdateDepartmentDto updateDepartmentDto,
            @PathVariable UUID departmentId, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        log.info("Update department by id {}", departmentId);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
                return ResponseEntity.ok(convertToDto(DepartmentDto.class, departmentService.updateDepartmentById(departmentId, updateDepartmentDto)));
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private <T> T convertToDto(Class<T> clazz, Department department) {
        return modelMapper.map(department, clazz);
    }

    private <T> Department convertToEntity(T dto) {
        return modelMapper.map(dto, Department.class);
    }
}
