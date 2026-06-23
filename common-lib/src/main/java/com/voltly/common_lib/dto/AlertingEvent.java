package com.voltly.common_lib.dto;

import lombok.Builder;

@Builder
public record AlertingEvent(
        Long userId,
        String message,
        double threshold,
        double energyConsumed,
        String email
) {
}

// Made with Bob
