package com.voltly.ingestion_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voltly.common_lib.dto.EnergyUsageDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsClient {
    @Value("${aws.sqs.energy-usage-queue}")
    private String energyUsageQueue;
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    public void send(EnergyUsageDto energyUsageDto) {
        try {
            sqsTemplate.send(energyUsageQueue, objectMapper.writeValueAsString(energyUsageDto));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize EnergyUsageDto", e);
        }
    }
}
