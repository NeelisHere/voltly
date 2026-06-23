package com.voltly.common_lib.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlertDto {
    Long id;
    Long userId;
    LocalDateTime createdAt;
    boolean sent;
}

// Made with Bob
