package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.SCodeDto;
import com.lvv.ttimpex2.dto.ShortSCodeDto;
import com.lvv.ttimpex2.dto.UpdateSCodeDto;
import com.lvv.ttimpex2.molel.SCode;
import com.lvv.ttimpex2.service.SCodeService;
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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = SCodeController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SCodeController {
    static final String REST_URL = "/api/scodes";
    static final String WRONG_CREDENTIALS = "Wrong credentials";

    private final SCodeService sCodeService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Get all scodes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all scodes",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortSCodeDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getAllSCodes() {
        log.info("Get all scodes");
//        return employeeService.getAllEmployed();
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(sCodeService.findAllSCodes().stream()
                .map(sCode -> convertToDto(ShortSCodeDto.class, sCode)).collect(Collectors.toList()));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get all scodes with employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all scodes  with employee",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SCodeDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/employees")
    public ResponseEntity<?> getAllSCodesWithEmployees() {
        log.info("Get all scodes with employee");
//        return employeeService.getAllEmployed();
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(sCodeService.findAllSCodes().stream()
                .map(sCode -> convertToDto(SCodeDto.class, sCode)).collect(Collectors.toList()));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }


    @Operation(summary = "Get all scodes with empty employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all scodes with empty employee",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortSCodeDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/empty")
    public ResponseEntity<?> getAllSCodesWithEmptyEmployee() {
        log.info("Get all scodes with empty employee");
//        return employeeService.getAllEmployed();
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(sCodeService.findAllSCodesWithEmptyEmployee().stream()
                .map(sCode -> convertToDto(ShortSCodeDto.class, sCode)).collect(Collectors.toList()));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }


    @Operation(summary = "Get information about scode by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the scode by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortSCodeDto.class))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Scode not found by id",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getSCodeById(@Parameter(description = "Id scode") @PathVariable String id) {
        log.info("Get scode by id {}", id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(convertToDto(ShortSCodeDto.class, sCodeService.findById(id)));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Create new scode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Scode created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortSCodeDto.class))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find scode",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save scode",
                    content = @Content)})
    @PostMapping
    @Validated
    public ResponseEntity<?> createSCode(
            HttpServletRequest request,
            @RequestBody @Valid UpdateSCodeDto dto,
            Errors errors) {
        log.info("Create scode {}", dto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        SCode sCode = convertToSCode(dto);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return new ResponseEntity<>(
                convertToDto(ShortSCodeDto.class, sCodeService.createSCode(sCode)),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update sCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated sCode",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortSCodeDto.class))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find sCode",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save sCode",
                    content = @Content)})
    @PatchMapping("/{id}")
    @Validated
    public ResponseEntity<?> updateSCode(
            HttpServletRequest request,
            @RequestBody @Valid UpdateSCodeDto dto,
            @PathVariable String id, Errors errors) {
        log.info("Update sCode by id {}", id);
//        System.out.println(employeeDto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        SCode sCode = convertToSCode(dto);
        sCode.setId(id);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return ResponseEntity.ok(convertToDto(ShortSCodeDto.class, sCodeService.updateSCode(sCode)));
    }

    @Operation(summary = "Clear employee in sCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cleaned employee in sCode",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortSCodeDto.class))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find sCode",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save sCode",
                    content = @Content)})
    @PatchMapping("/clear/{id}")
    @Validated
    public ResponseEntity<?> clearEmployeeInSCode(
            @PathVariable String id) {
        log.info("Clear employee in sCode by id {}", id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        SCode sCode = sCodeService.findById(id);
        sCode.setEmployee(null);
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return ResponseEntity.ok(convertToDto(ShortSCodeDto.class, sCodeService.saveSCode(sCode)));
    }

    @Operation(summary = "Delete scode by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the scode by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShortSCodeDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Scode not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSCodeById(@Parameter(description = "Id SCode") @PathVariable String id) {
        log.info("delete scode by id {}", id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return  ResponseEntity.ok(convertToDto(ShortSCodeDto.class, sCodeService.deleteSCodeById(id)));
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private <T> T convertToDto(Class<T> clazz, SCode sCode) {
        return modelMapper.map(sCode, clazz);
    }

    private <T> SCode convertToSCode(T dto) {
        return modelMapper.map(dto, SCode.class);
    }

}