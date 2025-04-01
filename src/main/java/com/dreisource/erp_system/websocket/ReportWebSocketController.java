package com.dreisource.erp_system.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ReportWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(ReportWebSocketController.class);

    @MessageMapping("/report")
    @SendTo("/topic/reports")
    public String handleReportMessage(String message) {
        logger.info("Received WebSocket Message: {}", message);
        return "Report Update: " + message;
    }
}

