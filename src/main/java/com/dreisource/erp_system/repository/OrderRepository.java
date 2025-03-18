package com.dreisource.erp_system.repository;

import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Add method to fetch orders by user
    List<Order> findByUser(User user);
}