package com.dreisource.erp_system.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendSms(String to, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        System.out.println("SMS Sent: " + message.getSid());
    }
}
