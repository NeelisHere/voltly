package com.voltly.common_lib.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String httpStatus;

    public UserNotFoundException(Long userId, String httpStatus) {
        super(String.format("User: %d not found!", userId));
        this.httpStatus = httpStatus;
    }
}
