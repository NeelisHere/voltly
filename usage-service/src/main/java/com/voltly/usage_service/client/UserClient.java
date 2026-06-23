package com.voltly.usage_service.client;

import com.voltly.common_lib.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class UserClient {
    @Value("${user.service.base-url:http://localhost:8081}")
    private String userServiceBaseUrl;
    
    private final RestClient restClient = RestClient.create();

    public UserDto findById(Long userId) {
        return restClient.get()
                .uri(userServiceBaseUrl + "/api/v1/users/{userId}", userId)
                .retrieve()
                .body(UserDto.class);
    }
}
