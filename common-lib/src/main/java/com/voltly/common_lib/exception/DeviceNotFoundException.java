package com.voltly.common_lib.exception;

import lombok.Getter;

@Getter
public class DeviceNotFoundException extends RuntimeException {
    private final String httpStatus;

    public DeviceNotFoundException(Long deviceId, String httpStatus) {
        super(String.format("Device: %d not found!", deviceId));
        this.httpStatus = httpStatus;
    }
}

