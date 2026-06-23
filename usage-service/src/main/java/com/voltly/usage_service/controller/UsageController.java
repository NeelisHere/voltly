package com.voltly.usage_service.controller;

import com.voltly.common_lib.dto.DeviceEnergy;
import com.voltly.common_lib.dto.UsageDto;
import com.voltly.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/usage")
public class UsageController {
    public final UsageService usageService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<DeviceEnergy>> getDeviceUsageByUser(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "days", defaultValue = "3") int days
    ) {
        return ResponseEntity.ok(usageService.getDeviceUsageByUser(userId, days));
    }
}
