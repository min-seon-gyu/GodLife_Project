package com.example.web.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e) {
        return handleExceptionInternal(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorResponse.ValidationError> collect =
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> ErrorResponse.ValidationError.of(error))
                        .collect(Collectors.toList());
        return handleExceptionInternal(ErrorCode.BAD_REQUEST, "유효성 검사를 실패했습니다.", collect);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return handleExceptionInternal(ErrorCode.METHOD_NOT_ALLOWED, "해당 방식을 지원하지 않습니다.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return handleExceptionInternal(ErrorCode.BAD_REQUEST, "타입이 불일치 합니다.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return handleExceptionInternal(ErrorCode.NOT_FOUND, "해당 리소스를 찾을 수 없습니다.");
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode, message));
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode, String message, List<ErrorResponse.ValidationError> errors) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode, message, errors));
    }
}
