package com.dreisource.erp_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_logs")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private String eventType; // Payment Created, Payment Completed, Refund Requested, Refund Processed

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    public TransactionLog() {}

    public TransactionLog(Payment payment, String eventType, String details, LocalDateTime eventDate) {
        this.payment = payment;
        this.eventType = eventType;
        this.details = details;
        this.eventDate = eventDate;
    }

    public Long getId() { return id; }
    public Payment getPayment() { return payment; }
    public String getEventType() { return eventType; }
    public String getDetails() { return details; }
    public LocalDateTime getEventDate() { return eventDate; }
}