package com.voltly.alert_service.controller;

import com.voltly.alert_service.service.AlertService;
import com.voltly.common_lib.dto.AlertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<AlertDto>> findAlertsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(alertService.findAlertsByUserId(userId));
    }
}

