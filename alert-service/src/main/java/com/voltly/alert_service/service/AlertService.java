package com.voltly.alert_service.service;

import com.voltly.alert_service.Repository.AlertRepository;
import com.voltly.alert_service.client.UserClient;
import com.voltly.alert_service.entity.Alert;
import com.voltly.alert_service.mapper.AlertMapper;
import com.voltly.common_lib.dto.AlertDto;
import com.voltly.common_lib.dto.AlertingEvent;
import com.voltly.common_lib.dto.UserDto;
import com.voltly.common_lib.exception.EmailSendingException;
import com.voltly.common_lib.exception.UserNotFoundException;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {
    public static final String ALERT_QUEUE = "voltly-alert";
    private final EmailService emailService;
    private final UserClient userClient;
    private final AlertRepository alertRepository;

    @SqsListener(ALERT_QUEUE)
    public void receive(AlertingEvent alertingEvent) {
        log.info("alertingEvent: {}", alertingEvent);

        UserDto user = null;

        try {
            user = userClient.findById(alertingEvent.userId());
        } catch (Exception e) {
            log.error("Error fetching user with id: {}", alertingEvent.userId(), e);
            throw new UserNotFoundException(alertingEvent.userId(), HttpStatus.NOT_FOUND.toString());
        }

        try {
            emailService.sendEmail(user.getEmail());
            Alert alert = Alert.builder()
                    .userId(user.getId())
                    .sent(true)
                    .build();
            alertRepository.saveAndFlush(alert);
        } catch (Exception e) {
            log.error("Error sending email to: {}", user.getEmail(), e);
            Alert alert = Alert.builder()
                    .userId(user.getId())
                    .sent(false)
                    .build();
            alertRepository.saveAndFlush(alert);
            throw new EmailSendingException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public List<AlertDto> findAlertsByUserId(Long userId) {
        // Check if user exists
        try {
            userClient.findById(userId);
        } catch (Exception e) {
            log.error("User not found with id: {}", userId, e);
            throw new UserNotFoundException(userId, HttpStatus.NOT_FOUND.toString());
        }

        List<Alert> alerts = alertRepository.findByUserId(userId);
        
        return alerts.stream()
                .map(AlertMapper::toDto)
                .collect(Collectors.toList());
    }
}
