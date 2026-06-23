package com.voltly.common_lib.exception;

import lombok.Getter;

@Getter
public class AlertNotFoundException extends RuntimeException {
    private final String httpStatus;

    public AlertNotFoundException(Long alertId, String httpStatus) {
        super(String.format("Alert: %d not found!", alertId));
        this.httpStatus = httpStatus;
    }
}

// Made with Bob
