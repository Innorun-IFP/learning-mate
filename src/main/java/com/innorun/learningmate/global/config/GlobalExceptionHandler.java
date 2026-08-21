package com.innorun.learningmate.global.config;

import com.innorun.learningmate.global.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        return ResponseEntity
                    .status(ex.getStatus())
                    .body(ex.getMessage());
    }
}

