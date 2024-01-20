package com.example.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;
import java.util.Collections;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int code;
    private final String name;
    private final String message;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;


    public ErrorResponse(ErrorCode errorCode, String message) {
        this(errorCode, message, Collections.emptyList());
    }

    public ErrorResponse(ErrorCode errorCode, String message, List<ValidationError> errors) {
        this.code = errorCode.getHttpStatus().value();
        this.name = errorCode.getHttpStatus().name();
        this.message = message;
        this.errors = errors;
    }

    @Builder
    @Getter
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
