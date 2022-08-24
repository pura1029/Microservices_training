package com.classpath.ordersapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleInvalidOrderId(Exception exception){
        return ResponseEntity.status(NOT_FOUND).body(new Error(101, exception.getMessage()));
    }
}

@RequiredArgsConstructor
@Setter
@Getter
class Error {
    private final int code;
    private final String message;
}