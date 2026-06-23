package com.voltly.usage_service.client;

import com.voltly.common_lib.dto.DeviceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class DeviceClient {
    @Value("${device.service.base-url:http://localhost:8082}")
    private String deviceServiceBaseUrl;
    
    private final RestClient restClient = RestClient.create();

    public DeviceDto findById(Long deviceId) {
        return restClient.get()
                .uri(deviceServiceBaseUrl + "/api/v1/devices/{deviceId}", deviceId)
                .retrieve()
                .body(DeviceDto.class);
    }
}
