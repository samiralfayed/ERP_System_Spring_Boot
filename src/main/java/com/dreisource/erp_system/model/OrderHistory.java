package com.dreisource.erp_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public OrderHistory() {}

    public OrderHistory(Order order, String status, LocalDateTime timestamp) {
        this.order = order;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
