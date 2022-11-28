package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.SettingDto;
import com.lvv.ttimpex2.dto.UpdateSettingDto;
import com.lvv.ttimpex2.molel.Setting;
import com.lvv.ttimpex2.service.SettingService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = SettingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SettingController {
    static final String REST_URL = "/api/settings";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final SettingService settingService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Operation(summary = "Get all settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all settings",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SettingDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping
    public ResponseEntity<?> getAllSettings() {
        log.info("Get all settings");
        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(settingService.getAllSettings());
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get information about setting by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the setting by date",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SettingDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "setting not found by date",
                    content = @Content)})
    @GetMapping("/{date}")
    public ResponseEntity<?> getSettingByDate(
            @Parameter(description = "date")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        log.info("Get setting by date {}", date);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        return ResponseEntity.ok(settingService.getSettingByDate(date.plusDays(1)));
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Create new setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setting created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SettingDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find setting",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save setting",
                    content = @Content)})
    @PostMapping
    @Validated
    public ResponseEntity<?> createSetting(
            HttpServletRequest request,
            @RequestBody @Valid UpdateSettingDto dto,
            Errors errors) {
        log.info("Create setting {}", dto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        Setting setting = settingService.createSetting(dto);
//        System.out.println(setting);
//        } else {
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return ResponseEntity.ok(convertToDto(SettingDto.class, setting));
    }

    @Operation(summary = "Update setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated setting",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SettingDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find setting",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save setting",
                    content = @Content)})
    @PatchMapping("/{date}")
    @Validated
    public ResponseEntity<?> updateSetting(
            HttpServletRequest request,
            @RequestBody @Valid SettingDto dto,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
            LocalDate date, Errors errors) {
        log.info("Update setting by date {}", date);
//        System.out.println(dto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
        Setting setting = settingService.updateSettingByDate(date, dto);
//        System.out.println(setting);
//        } else {
//    }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
        return new ResponseEntity<>(
                convertToDto(SettingDto.class, setting),
                HttpStatus.CREATED
        );
    }

    private <T> T convertToDto(Class<T> clazz, Setting setting) {
        return modelMapper.map(setting, clazz);
    }
}
