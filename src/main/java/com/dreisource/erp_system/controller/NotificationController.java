package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.NotificationRequest;
import com.dreisource.erp_system.service.EmailService;
import com.dreisource.erp_system.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/email")
    public String sendEmail(@RequestBody NotificationRequest request) {
        try {
            emailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    @PostMapping("/sms")
    public String sendSms(@RequestBody NotificationRequest request) {
        smsService.sendSms(request.getTo(), request.getMessage());
        return "SMS sent successfully!";
    }
}