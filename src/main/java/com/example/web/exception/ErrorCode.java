package com.example.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor @Getter
public enum ErrorCode{
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);
    private final HttpStatus httpStatus;
}
