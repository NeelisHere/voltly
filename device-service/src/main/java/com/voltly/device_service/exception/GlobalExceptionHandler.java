package com.voltly.device_service.exception;

import com.voltly.common_lib.exception.DeviceNotFoundException;
import com.voltly.common_lib.exception.ErrorResponse;
import com.voltly.common_lib.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeviceNotFoundException(DeviceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getHttpStatus(), LocalDateTime.now());
        return ResponseEntity
                .status(Integer.parseInt(e.getHttpStatus()))
                .body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getHttpStatus(), LocalDateTime.now());
        return ResponseEntity
                .status(Integer.parseInt(e.getHttpStatus()))
                .body(errorResponse);
    }
}
