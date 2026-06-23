package com.voltly.api_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/fallback")
public class FallbackController {
    @RequestMapping(path = "/users")
    private Mono<ResponseEntity<Map<String, String>>> userServiceFallback() {
        return fallback("user-service");
    }

    @RequestMapping(path = "/devices")
    private Mono<ResponseEntity<Map<String, String>>> deviceServiceFallback() {
        return fallback("device-service");
    }

    @RequestMapping(path = "/ingestion")
    private Mono<ResponseEntity<Map<String, String>>> ingestionServiceFallback() {
        return fallback("ingestion-service");
    }

    @RequestMapping(path = "/usage")
    private Mono<ResponseEntity<Map<String, String>>> usageServiceFallback() {
        return fallback("usage-service");
    }

    @RequestMapping(path = "/alerts")
    private Mono<ResponseEntity<Map<String, String>>> alertServiceFallback() {
        return fallback("alert-service");
    }

    private Mono<ResponseEntity<Map<String, String>>> fallback(String serviceId) {
        log.info("Service temporarily unavailable, please try again later!");
        ResponseEntity<Map<String, String>> response = ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("message", serviceId + " temporarily unavailable, please try again later!"));
        return Mono.just(response);
    }
}
