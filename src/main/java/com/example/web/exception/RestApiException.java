package com.example.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;
}
