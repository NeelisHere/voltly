package com.voltly.alert_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public static final String FROM = "no-reply@voltly.com";
    public static final String SUBJECT = "Energy Usage Exceeding Threshold";
    public static final String EMAIL_BODY = "Energy Usage Exceeding Threshold";

    public void sendEmail(String to) {
        log.info("email sent");
        log.info("to: {}", to);
        log.info("from: {}", FROM);
        log.info("sub: {}", SUBJECT);
        log.info("{}", EMAIL_BODY);
    }
}

