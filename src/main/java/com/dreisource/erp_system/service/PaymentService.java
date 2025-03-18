package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.Payment;
import com.dreisource.erp_system.model.Order;
import java.util.Optional;

public interface PaymentService {
    Payment processPayment(Order order, String paymentMethod);
    Payment requestRefund(Long orderId);
    Payment refundPayment(Long orderId);

    // ✅ Add missing method
    Optional<Payment> getPaymentById(Long paymentId);

    // ✅ Add deletePayment method
    boolean deletePayment(Long paymentId);
}