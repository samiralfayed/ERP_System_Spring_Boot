package com.dreisource.erp_system.serviceimpl;

import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.OrderHistory;
import com.dreisource.erp_system.model.Product;
import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.repository.OrderHistoryRepository;
import com.dreisource.erp_system.repository.OrderRepository;
import com.dreisource.erp_system.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderHistoryRepository orderHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public Order createOrder(User user, Product product, int quantity) {
        Order order = new Order(user, product, quantity, "Pending");
        order = orderRepository.save(order);
        recordOrderHistory(order.getId(), "Pending");
        return order;
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = findById(id);
        order.setStatus(status);
        recordOrderHistory(order.getId(), status);
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            Order order = findById(id);
            order.setStatus("CANCELLED");
            orderRepository.save(order);
            recordOrderHistory(id, "CANCELLED");
            return true;
        }
        return false;
    }

    @Override
    public void recordOrderHistory(Long orderId, String status) {
        Order order = findById(orderId);
        OrderHistory history = new OrderHistory(order, status, LocalDateTime.now());
        orderHistoryRepository.save(history);
    }

    @Override
    public void notifyWebhook(Order order) {
        // Mock webhook notification (e.g., send event to an external system)
        System.out.println("📢 Webhook: Order " + order.getId() + " updated to " + order.getStatus());
    }

    @Override
    public List<OrderHistory> getOrderHistory(Long orderId) {
        return orderHistoryRepository.findByOrderId(orderId);
    }

    // ✅ Implement method to get user orders
    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}
