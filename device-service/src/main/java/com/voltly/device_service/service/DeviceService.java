package com.voltly.device_service.service;

import com.voltly.common_lib.exception.DeviceNotFoundException;
import com.voltly.common_lib.exception.UserNotFoundException;
import com.voltly.common_lib.dto.DeviceDto;
import com.voltly.device_service.entity.Device;
import com.voltly.device_service.mapper.DeviceMapper;
import com.voltly.device_service.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public boolean deviceExistsById(Long deviceId) {
        return deviceRepository.existsById(deviceId);
    }

    public boolean userExistsById(Long userId) {
        return deviceRepository.existsById(userId);
    }

    public DeviceDto findById(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId, String.valueOf(HttpStatus.NOT_FOUND.value())));
        return DeviceMapper.toDto(device);
    }

    public DeviceDto registerDevice(DeviceDto deviceDto) {
        if (!userExistsById(deviceDto.getUserId())) {
            throw new UserNotFoundException(deviceDto.getUserId(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        Device savedDevice = deviceRepository.save(DeviceMapper.toEntity(deviceDto));
        return DeviceMapper.toDto(savedDevice);
    }

    public DeviceDto updateDevice(DeviceDto deviceDto) {
        Device device = deviceRepository.findById(deviceDto.getId())
                .orElseThrow(() -> new DeviceNotFoundException(deviceDto.getId(), String.valueOf(HttpStatus.NOT_FOUND.value())));

        if (!userExistsById(deviceDto.getUserId())) {
            throw new UserNotFoundException(deviceDto.getUserId(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        device.setName(deviceDto.getName());
        device.setType(deviceDto.getType());
        device.setLocation(deviceDto.getLocation());
        device.setUserId(deviceDto.getUserId());

        Device updatedDevice = deviceRepository.save(device);
        return DeviceMapper.toDto(updatedDevice);
    }

    public void deleteById(Long deviceId) {
        if (!deviceExistsById(deviceId)) {
            throw new UserNotFoundException(deviceId, String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        deviceRepository.deleteById(deviceId);
    }
}
