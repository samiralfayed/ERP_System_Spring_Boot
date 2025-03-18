package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.Payment;
import com.dreisource.erp_system.service.OrderService;
import com.dreisource.erp_system.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    // ✅ Process Payment
    @PostMapping("/{orderId}")
    public ResponseEntity<?> processPayment(@PathVariable Long orderId, @RequestParam String paymentMethod) {
        Order order = orderService.findById(orderId);
        Payment payment = paymentService.processPayment(order, paymentMethod);
        return ResponseEntity.ok(payment);
    }

    // ✅ Refund Payment (Admin Only)
    @PutMapping("/{orderId}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> refundPayment(@PathVariable Long orderId) {
        Payment payment = paymentService.refundPayment(orderId);
        return ResponseEntity.ok(payment);
    }

    // ✅ Request Refund (Users)
    @PutMapping("/{orderId}/request-refund")
    public ResponseEntity<?> requestRefund(@PathVariable Long orderId) {
        Payment payment = paymentService.requestRefund(orderId);
        return ResponseEntity.ok(payment);
    }

    // ✅ Approve & Process Refund (Admin Only)
    @PutMapping("/{orderId}/approve-refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> processRefund(@PathVariable Long orderId) {
        Payment payment = paymentService.refundPayment(orderId);
        return ResponseEntity.ok(payment);
    }

    // ✅ Get Payment Details
    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentService.getPaymentById(paymentId);

        if (payment.isEmpty()) {
            return ResponseEntity.status(404).body("Payment not found");
        }

        return ResponseEntity.ok(payment.get());
    }

    // ✅ Delete Payment (Admin Only)
    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePayment(@PathVariable Long paymentId) {
        boolean deleted = paymentService.deletePayment(paymentId);

        if (!deleted) {
            return ResponseEntity.status(404).body("Payment not found");
        }

        return ResponseEntity.ok("Payment deleted successfully");
    }
}
