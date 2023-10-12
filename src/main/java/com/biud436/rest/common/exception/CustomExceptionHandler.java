package com.biud436.rest.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("status", status.value());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
}