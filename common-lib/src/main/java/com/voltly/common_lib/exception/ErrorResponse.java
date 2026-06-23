package com.voltly.common_lib.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ErrorResponse(
    String message, 
    String httpStatus, 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime timestamp
) {}
