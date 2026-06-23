package com.voltly.device_service.controller;

import com.voltly.common_lib.dto.DeviceDto;
import com.voltly.device_service.service.DeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping(path = "/{deviceId}")
    public ResponseEntity<DeviceDto> findById(@PathVariable(name = "deviceId") Long deviceId) {
        return ResponseEntity.ok(deviceService.findById(deviceId));
    }

    @PostMapping
    public ResponseEntity<DeviceDto> registerDevice(DeviceDto deviceDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deviceService.registerDevice(deviceDto));
    }

    @PutMapping
    public ResponseEntity<DeviceDto> updateDevice(DeviceDto deviceDto) {
        return ResponseEntity.ok(deviceService.updateDevice(deviceDto));
    }

    @DeleteMapping(path = "/{deviceId}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable(name = "deviceId") Long deviceId) {
        deviceService.deleteById(deviceId);
        return ResponseEntity.ok(Map.of("message", String.format("Device: %d deleted successfully!", deviceId)));
    }
}
