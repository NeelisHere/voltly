package com.voltly.ingestion_service;

import com.voltly.common_lib.dto.EnergyUsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionService {
    private final SqsClient sqsClient;

    public void ingestData(final EnergyUsageDto energyUsageDto) {
        log.info("energy usage info: {}", energyUsageDto);
        try {
            sqsClient.send(energyUsageDto);
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
