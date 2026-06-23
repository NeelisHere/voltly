package com.voltly.common_lib.exception;

public class EmailSendingException extends RuntimeException {
    private final String httpStatus;

    public EmailSendingException(String message, String httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public String getHttpStatus() {
        return httpStatus;
    }
}
