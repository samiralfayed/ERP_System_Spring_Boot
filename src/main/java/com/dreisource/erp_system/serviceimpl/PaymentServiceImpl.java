package com.dreisource.erp_system.serviceimpl;

import com.dreisource.erp_system.model.Invoice;
import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.Payment;
import com.dreisource.erp_system.model.TransactionLog;
import com.dreisource.erp_system.repository.InvoiceRepository;
import com.dreisource.erp_system.repository.OrderRepository;
import com.dreisource.erp_system.repository.PaymentRepository;
import com.dreisource.erp_system.repository.TransactionLogRepository;
import com.dreisource.erp_system.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final TransactionLogRepository transactionLogRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
                              InvoiceRepository invoiceRepository, TransactionLogRepository transactionLogRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public Payment processPayment(Order order, String paymentMethod) {
        String transactionId = "TXN-" + System.currentTimeMillis();
        Payment payment = new Payment(order, order.getProduct().getPrice() * order.getQuantity(),
                paymentMethod, transactionId, "Completed", LocalDateTime.now());

        payment = paymentRepository.save(payment);
        transactionLogRepository.save(new TransactionLog(payment, "Payment Completed",
                "Transaction ID: " + transactionId, LocalDateTime.now()));

        // ✅ Generate Invoice after Payment
        generateInvoice(order, payment);

        sendWebhook(payment, "Payment Successful");
        return payment;
    }

    // ✅ Separate method for invoice creation
    private void generateInvoice(Order order, Payment payment) {
        Invoice invoice = new Invoice(order, payment, payment.getAmount());
        invoiceRepository.save(invoice);
    }

    @Override
    public Payment requestRefund(Long orderId) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("No payment found for order ID: " + orderId);
        }
        Payment payment = paymentOpt.get();
        payment.setStatus("Approved for Refund");
        transactionLogRepository.save(new TransactionLog(payment, "Refund Requested",
                "Awaiting admin approval", LocalDateTime.now()));
        return paymentRepository.save(payment);
    }

    @Override
    public Payment refundPayment(Long orderId) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        if (paymentOpt.isEmpty()) {
            throw new RuntimeException("No payment found for order ID: " + orderId);
        }

        Payment payment = paymentOpt.get();
        if (!"Approved for Refund".equals(payment.getStatus())) {
            throw new RuntimeException("Refund not approved for this payment.");
        }

        payment.setStatus("Refunded");
        transactionLogRepository.save(new TransactionLog(payment, "Refund Processed",
                "Payment has been refunded", LocalDateTime.now()));

        sendWebhook(payment, "Refund Successful");
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public boolean deletePayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isEmpty()) {
            return false;
        }
        paymentRepository.deleteById(paymentId);
        return true;
    }

    private void sendWebhook(Payment payment, String event) {
        System.out.println("\ud83d\udce2 Webhook: " + event + " for Order " + payment.getOrder().getId());
    }
}
