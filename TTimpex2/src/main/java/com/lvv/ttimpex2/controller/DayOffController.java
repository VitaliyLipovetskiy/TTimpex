package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.*;
import com.lvv.ttimpex2.molel.DayOff;
import com.lvv.ttimpex2.molel.EmployeeDate;
import com.lvv.ttimpex2.service.DayOffService;
import com.lvv.ttimpex2.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = DayOffController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class DayOffController {
    static final String REST_URL = "/api/days_off/employees";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final DayOffService dayOffService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Get all days off in a month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all days off in a month",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Arrays.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping
    public ResponseEntity<?>  getBetweenDaysOffTo(@RequestParam @Nullable String filterMonth) {
        log.info("getBetweenDaysOffTo {}", filterMonth);
        String[] parts = new String[0];
        if (filterMonth != null) {
            parts = filterMonth.split("-");
        }
        int year, month;
        try {
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return ResponseEntity.ok(dayOffService.getAllEmployeesWithDaysOff(startDate, endDate));
    }

    @Operation(summary = "Create new department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DayOffAndWorkedDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find department",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save department",
                    content = @Content)})
    @PatchMapping(value = "/{employeeId}")
    @Validated
    public ResponseEntity<?> updateDayOffByEmployeeId(
            HttpServletRequest request,
            @Valid @PathVariable UUID employeeId,
            @Valid @RequestBody UpdateDayOfDto dto,
            Errors errors) {
        log.info("update day off by employeeId {} {}", employeeId, dto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        DayOff dayOff = dayOffService.updateDayOffByEmployeeId(employeeId, convertToDayOf(dto));

        log.info("dayOff {}", dayOff);

        return ResponseEntity.ok(convertToDto(DayOffAndWorkedDto.class, dayOff));
    }

    private DayOff convertToDayOf(UpdateDayOfDto dto) {
        EmployeeDate employeeDate = modelMapper.map(dto, EmployeeDate.class);
        DayOff dayOff = modelMapper.map(dto, DayOff.class);
        dayOff.setEmployeeDate(employeeDate);
        return dayOff;
    }

    private <T> T convertToDto(Class<T> clazz, DayOff dayOff) {
        return modelMapper.map(dayOff, clazz);
    }

}
