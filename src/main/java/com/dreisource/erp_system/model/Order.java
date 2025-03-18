package com.dreisource.erp_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status; // ✅ Order status (Pending, Shipped, Delivered)

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public Order(User user, Product product, int quantity, String status) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }

    // ✅ Add setter method for status
    public void setStatus(String status) {
        this.status = status;
    }
}
