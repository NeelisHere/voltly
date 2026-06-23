package com.voltly.alert_service.mapper;

import com.voltly.alert_service.entity.Alert;
import com.voltly.common_lib.dto.AlertDto;

public final class AlertMapper {

    private AlertMapper() {
        // Prevent instantiation
    }

    public static AlertDto toDto(Alert alert) {
        if (alert == null) {
            return null;
        }

        return AlertDto.builder()
                .id(alert.getId())
                .userId(alert.getUserId())
                .createdAt(alert.getCreatedAt())
                .sent(alert.isSent())
                .build();
    }

    public static Alert toEntity(AlertDto alertDto) {
        if (alertDto == null) {
            return null;
        }

        return Alert.builder()
                .userId(alertDto.getUserId())
                .createdAt(alertDto.getCreatedAt())
                .sent(alertDto.isSent())
                .build();
    }
}