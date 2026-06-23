package com.voltly.usage_service.mapper;

import com.voltly.common_lib.dto.EnergyUsageDto;
import com.voltly.usage_service.entity.EnergyUsage;

public final class UsageMapper {

    private UsageMapper() {
        // Prevent instantiation
    }

    public static EnergyUsageDto toDto(EnergyUsage energyUsage) {
        if (energyUsage == null) {
            return null;
        }

        return new EnergyUsageDto(
                energyUsage.getDeviceId(),
                energyUsage.getEnergyConsumed(),
                energyUsage.getTimestamp()
        );
    }

    public static EnergyUsage toEntity(EnergyUsageDto energyUsageDto) {
        if (energyUsageDto == null) {
            return null;
        }

        return EnergyUsage.builder()
                .deviceId(energyUsageDto.deviceId())
                .energyConsumed(energyUsageDto.energyConsumed())
                .timestamp(energyUsageDto.timestamp())
                .build();
    }
}