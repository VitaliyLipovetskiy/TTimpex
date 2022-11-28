package com.lvv.ttimpex2.controller;

import com.lvv.ttimpex2.dto.InitTimeStampDto;
import com.lvv.ttimpex2.dto.UpdateEmployeeDto;
import com.lvv.ttimpex2.service.InitService;
import com.lvv.ttimpex2.validation.ValidationErrorBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = InitController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class InitController {
    static final String REST_URL = "/api/init";

    private final InitService initService;

    @PostMapping("/employees")
    public ResponseEntity<?> checkingEmployeesWithParadoxDB() {
        log.info("Init employee");
        return ResponseEntity.ok(initService.checkingEmployeesWithParadoxDB());
    }

    @PostMapping("/s_code")
    public ResponseEntity<?> checkingSCodeWithParadoxDB() {
        log.info("Init s_code");
        return ResponseEntity.ok(initService.checkSCodeWithParadoxDB());
    }

    @PostMapping("/time_stamp")
    @Validated
    public ResponseEntity<?> checkingTimeStampWithParadoxDB(
            HttpServletRequest request,
            @RequestBody @Valid InitTimeStampDto dto,
            Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        log.info("Init time_stamp from {} to {}", dto.getStartDate(), dto.getEndDate());
        return ResponseEntity.ok(initService.checkingTimeStampWithParadoxDB(dto));
    }
}
