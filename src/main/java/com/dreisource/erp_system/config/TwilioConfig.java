package com.dreisource.erp_system.config;

import com.twilio.Twilio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    // Replace these with your actual Twilio credentials
    private static final String ACCOUNT_SID = "your_account_sid";
    private static final String AUTH_TOKEN = "your_auth_token";

    @Bean
    public TwilioInitializer twilioInitializer() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return new TwilioInitializer();
    }

    // Dummy class to satisfy @Bean return requirement
    public static class TwilioInitializer {
        public TwilioInitializer() {
            System.out.println("Twilio initialized successfully.");
        }
    }
}
