package com.voltly.usage_service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voltly.common_lib.dto.AlertDto;
import com.voltly.common_lib.dto.AlertingEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsClient {
    @Value("${aws.sqs.alert-queue}")
    public String alertQueue;
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    public void send(AlertingEvent alertingEvent) {
        try {
            sqsTemplate.send(alertQueue, objectMapper.writeValueAsString(alertingEvent));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize AlertDto", e);
        }
    }
}


