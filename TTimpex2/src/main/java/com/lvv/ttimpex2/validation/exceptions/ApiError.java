package com.lvv.ttimpex2.validation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {
    private List<String> errors;
    private String message;
    private HttpStatus status;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public ApiError(final String error, final String message, final HttpStatus status) {
        this.errors = Collections.singletonList(error);
        this.message = message;
        this.status = status;
    }

    public ApiError(List<String> errors, String message, HttpStatus status) {
        this.errors = errors;
        this.message = message;
        this.status = status;
    }

    public void setError(final String error) {
        this.errors = Collections.singletonList(error);
    }
}
