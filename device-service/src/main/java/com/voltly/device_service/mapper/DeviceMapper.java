package com.voltly.device_service.mapper;

import com.voltly.common_lib.dto.DeviceDto;
import com.voltly.device_service.entity.Device;

public final class DeviceMapper {

    private DeviceMapper() {
        // Prevent instantiation
    }

    public static DeviceDto toDto(Device device) {
        if (device == null) {
            return null;
        }

        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }

    public static Device toEntity(DeviceDto deviceDto) {
        if (deviceDto == null) {
            return null;
        }

        return Device.builder()
                .name(deviceDto.getName())
                .type(deviceDto.getType())
                .location(deviceDto.getLocation())
                .userId(deviceDto.getUserId())
                .build();
    }
}