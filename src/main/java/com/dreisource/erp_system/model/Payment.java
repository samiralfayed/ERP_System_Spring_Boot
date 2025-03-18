package com.dreisource.erp_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentMethod;  // Credit Card, PayPal, Bank Transfer

    @Column(nullable = false)
    private String transactionId; // Unique ID from Payment Gateway

    @Column(nullable = false)
    private String status;  // Pending, Completed, Failed, Refunded, Approved for Refund

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public Payment() {}

    public Payment(Order order, double amount, String paymentMethod, String transactionId, String status, LocalDateTime paymentDate) {
        this.order = order;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public String getStatus() { return status; }
    public LocalDateTime getPaymentDate() { return paymentDate; }

    public void setStatus(String status) { this.status = status; }
}