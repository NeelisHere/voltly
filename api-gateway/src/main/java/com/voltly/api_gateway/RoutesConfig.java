package com.voltly.api_gateway;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class RoutesConfig {
    @Value("${user.service.base-url}")
    private String userServiceBaseUrl;
    @Value("${user.service.fallback-url}")
    private String userServiceFallbackUrl;

    @Value("${device.service.base-url}")
    private String deviceServiceBaseUrl;
    @Value("${device.service.fallback-url}")
    private String deviceServiceFallbackUrl;

    @Value("${ingestion.service.base-url}")
    private String ingestionServiceBaseUrl;
    @Value("${ingestion.service.fallback-url}")
    private String ingestionServiceFallbackUrl;

    @Value("${usage.service.base-url}")
    private String usageServiceBaseUrl;
    @Value("${usage.service.fallback-url}")
    private String usageServiceFallbackUrl;

    @Value("${alert.service.base-url}")
    private String alertServiceBaseUrl;
    @Value("${alert.service.fallback-url}")
    private String alertServiceFallbackUrl;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(createRoute("/api/v1/users/**", userServiceBaseUrl, userServiceFallbackUrl))
                .route(createRoute("/api/v1/devices/**", deviceServiceBaseUrl, deviceServiceFallbackUrl))
                .route(createRoute("/api/v1/ingestion/**", ingestionServiceBaseUrl, ingestionServiceFallbackUrl))
                .route(createRoute("/api/v1/usage/**", usageServiceBaseUrl, usageServiceFallbackUrl))
                .route(createRoute("/api/v1/alerts/**", alertServiceBaseUrl, alertServiceFallbackUrl))
                .build();
    }

    public Function<PredicateSpec, Buildable<Route>> createRoute(String path, String serviceUrl, String fallbackUrl) {
        return (routeSpec) -> routeSpec
                .path(path)
                .uri(serviceUrl);
    }
}
