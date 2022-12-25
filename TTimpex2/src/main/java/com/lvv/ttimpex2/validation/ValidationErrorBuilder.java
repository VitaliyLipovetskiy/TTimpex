package com.lvv.ttimpex2.validation;

import org.springframework.validation.Errors;

public class ValidationErrorBuilder {
    private ValidationErrorBuilder() {
    }

    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError(
                "Validation failure: " + errors.getErrorCount() + " errors.");
        errors.getAllErrors().forEach(e -> error.addValidationError(e.getDefaultMessage()));
        return error;
    }
}
