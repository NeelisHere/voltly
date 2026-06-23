package com.voltly.ingestion_service;


import com.voltly.common_lib.dto.EnergyUsageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ingestion")
public class IngestionController {
    private final IngestionService ingestionService;

    @PostMapping
    public ResponseEntity<?> ingestData(@RequestBody EnergyUsageDto energyUsageDto) {
        ingestionService.ingestData(energyUsageDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Data has been successfully placed in the queue!",
                        "timestamp", LocalDateTime.now().toString()
                ));
    }
}
