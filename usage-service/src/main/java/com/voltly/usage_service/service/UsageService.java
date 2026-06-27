package com.voltly.usage_service.service;

import com.voltly.common_lib.dto.*;
import com.voltly.common_lib.exception.UserNotFoundException;
import com.voltly.usage_service.client.DeviceClient;
import com.voltly.usage_service.client.SqsClient;
import com.voltly.usage_service.client.UserClient;
import com.voltly.usage_service.mapper.UsageMapper;
import com.voltly.usage_service.repository.UsageRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsageService {
    public final UsageRepository usageRepository;
    public final DeviceClient deviceClient;
    public final UserClient userClient;
    public final SqsClient sqsClient;

    @SqsListener("${aws.sqs.energy-usage-queue}")
    public void receive(EnergyUsageDto energyUsageDto) {
        log.info("data received: {}", energyUsageDto);
        usageRepository.save(UsageMapper.toEntity(energyUsageDto));
    }

    public List<DeviceEnergy> getTotalEnergyUsageByDeviceInLastXHours(LocalDateTime time) {
        List<DeviceEnergy> deviceEnergyUsageList = usageRepository.getTotalEnergyUsageByDeviceInLastXHours(time);
        log.info("deviceEnergyUsageList: {}", deviceEnergyUsageList);
        for (DeviceEnergy deviceEnergy: deviceEnergyUsageList) {
            try {
                DeviceDto device = deviceClient.findById(deviceEnergy.getDeviceId());
                deviceEnergy.setUserId(device.getUserId());
            } catch (Exception e) {
                log.info("Error: {}", e.getMessage());
            }
        }
        return deviceEnergyUsageList;
    }

    public Map<Long, Double> getTotalEnergyUsagePerUser(List<DeviceEnergy> deviceEnergyUsageList) {
        return deviceEnergyUsageList.stream()
            .collect (
                Collectors.groupingBy (
                    DeviceEnergy::getDeviceId,
                    Collectors.summingDouble(d -> d.getEnergyConsumed() != null? d.getEnergyConsumed() : 0.0)
                )
            );
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void sendEmailToAllUsersExceedingEnergyUsageThreshold() {
        LocalDateTime fiveHoursAgo = LocalDateTime.now().minusHours(5);
        List<DeviceEnergy> deviceEnergyUsageList = getTotalEnergyUsageByDeviceInLastXHours(fiveHoursAgo);
        Map<Long, Double> totalEnergyUsagePerUser = getTotalEnergyUsagePerUser(deviceEnergyUsageList);
        log.info("energyPerUser: {}", totalEnergyUsagePerUser);
        for (var entry: totalEnergyUsagePerUser.entrySet()) {
            try {
                UserDto user = userClient.findById(entry.getKey());
                log.info("user: {}", user);
                if (entry.getValue() > user.getEnergyAlertingThreshold()) {
                    log.info("sending alert to: {}", user.getEmail());
                    final AlertingEvent alertingEvent = AlertingEvent.builder()
                            .userId(user.getId())
                            .message("Energy consumption threshold exceeded!")
                            .email(user.getEmail())
                            .threshold(user.getEnergyAlertingThreshold())
                            .energyConsumed(entry.getValue())
                            .build();
                    sqsClient.send(alertingEvent);
                }
            } catch (Exception e) {
                log.info("Error: {}", e.getMessage());
            }
        }
    }

    public List<DeviceEnergy> getDeviceUsageByUser(Long userId, int days) {
        try {
            userClient.findById(userId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(userId, e.getStatusCode().toString());
        }
        LocalDateTime time = LocalDateTime.now().minusHours(24L * days);
        return getTotalEnergyUsageByDeviceInLastXHours(time).stream()
                .filter(d -> Objects.equals(d.getUserId(), userId))
                .collect(Collectors.toList());
    }
}
