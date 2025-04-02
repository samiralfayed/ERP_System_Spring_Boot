package com.dreisource.erp_system.config;


import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class TwilioInitializer {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @PostConstruct
    public void initTwilio() {
        if (accountSid == null || authToken == null || phoneNumber == null) {
            throw new RuntimeException("Twilio credentials are missing!");
        }
        Twilio.init(accountSid, authToken);
        System.out.println("Twilio initialized with SID: " + accountSid);
    }
}