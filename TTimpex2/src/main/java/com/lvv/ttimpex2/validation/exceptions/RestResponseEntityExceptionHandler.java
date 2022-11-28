package com.lvv.ttimpex2.validation.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ApplicationException.class })
    @ResponseBody
    public ResponseEntity<Object> handleApplicationException(final ApplicationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        final ApiError apiError = new ApiError(ex.getLocalizedMessage(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

//    @ExceptionHandler({ AuthenticationException.class })
//    @ResponseBody
//    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
//        log.info(ex.getClass().getName());
//        final ApiError apiError = new ApiError(ex.getLocalizedMessage(), ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseBody
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        final ApiError apiError = new ApiError(ex.getLocalizedMessage(), ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        final String error = ex.getName() + " should be of type " + ex.getRequiredType();
        final ApiError apiError = new ApiError(error, ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ApiError apiError = new ApiError("error occurred", ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
