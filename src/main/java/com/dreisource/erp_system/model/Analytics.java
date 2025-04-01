package com.dreisource.erp_system.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String event;
    private String user;
    private LocalDateTime timestamp;
    private String category;
    private String source;
    private String dataType;
    private Double value;
    private String analysisResult;
    private String device;
    private String browser;
    private String ipAddress;
    private String location;
    private boolean deleted = false;

    // Default Constructor
    public Analytics() {}

    // Full Constructor
    public Analytics(String title, String event, String user, LocalDateTime timestamp, String category, String source,
                     String dataType, Double value, String analysisResult, String device, String browser, String ipAddress, String location) {
        this.title = title;
        this.event = event;
        this.user = user;
        this.timestamp = timestamp;
        this.category = category;
        this.source = source;
        this.dataType = dataType;
        this.value = value;
        this.analysisResult = analysisResult;
        this.device = device;
        this.browser = browser;
        this.ipAddress = ipAddress;
        this.location = location;
    }

    // Constructor for simplified creation
    public Analytics(String title, String event, String user, String category, double value) {
        this.title = title;
        this.event = event;
        this.user = user;
        this.timestamp = LocalDateTime.now(); // Auto-generate timestamp
        this.category = category;
        this.source = "Unknown Source";
        this.dataType = "Unknown DataType";
        this.value = value;
        this.analysisResult = "No Analysis";
        this.device = "Unknown Device";
        this.browser = "Unknown Browser";
        this.ipAddress = "0.0.0.0";
        this.location = "Unknown Location";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getAnalysisResult() { return analysisResult; }
    public void setAnalysisResult(String analysisResult) { this.analysisResult = analysisResult; }

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}