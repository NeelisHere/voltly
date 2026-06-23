package com.voltly.common_lib.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UsageDto(
    Long userId,
    List<DeviceDto> devices
) {
}

// Made with Bob
