package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.OrderHistory;
import com.dreisource.erp_system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderService {

    Order createOrder(User user, com.dreisource.erp_system.model.Product product, int quantity);

    Page<Order> getAllOrders(Pageable pageable);

    Order findById(Long id);

    Order updateOrderStatus(Long id, String status);

    boolean deleteOrder(Long id);

    void recordOrderHistory(Long orderId, String status);

    void notifyWebhook(Order order);

    List<OrderHistory> getOrderHistory(Long orderId);

    // ✅ Add method to get orders by user
    List<Order> getUserOrders(User user);
}
