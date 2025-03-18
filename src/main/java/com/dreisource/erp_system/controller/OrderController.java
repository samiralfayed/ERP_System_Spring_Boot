package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.Order;
import com.dreisource.erp_system.model.Product;
import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.payload.MessageResponse;
import com.dreisource.erp_system.security.JwtTokenProvider;
import com.dreisource.erp_system.service.OrderService;
import com.dreisource.erp_system.service.ProductService;
import com.dreisource.erp_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public OrderController(OrderService orderService, ProductService productService,
                           UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ✅ 1. Secure Order Creation (POST)
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam Long productId,
                                         @RequestParam int quantity) {

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productService.findById(productId);

        if (product.getStock() < quantity) {
            return ResponseEntity.badRequest().body(new MessageResponse("Insufficient stock!"));
        }

        product.setStock(product.getStock() - quantity);
        productService.updateProduct(product);

        Order order = orderService.createOrder(user, product, quantity);
        orderService.recordOrderHistory(order.getId(), "Pending");

        return ResponseEntity.ok(order);
    }

    // ✅ 2. Get All Orders (Only Admins)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Order>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // ✅ 3. Get User Orders (Users Can Only View Their Own Orders)
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    // ✅ 4. Get Order by ID (Admins or Order Owner)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Order order = orderService.findById(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body(new MessageResponse("Access Denied!"));
        }

        return ResponseEntity.ok(order);
    }

    // ✅ 5. Update Order Status (Admins Only)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        orderService.recordOrderHistory(id, status);
        orderService.notifyWebhook(updatedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    // ✅ 6. Soft Delete (Cancel) Order (Users & Admins)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> cancelOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Order order = orderService.findById(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body(new MessageResponse("Access Denied!"));
        }

        order.setStatus("CANCELLED");
        orderService.updateOrderStatus(id, "CANCELLED");
        orderService.recordOrderHistory(id, "CANCELLED");
        return ResponseEntity.ok(new MessageResponse("Order cancelled successfully."));
    }

    // ✅ 7. Get Order History (Admins or Order Owner)
    @GetMapping("/{id}/history")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderHistory(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Order order = orderService.findById(id);

        if (!order.getUser().getUsername().equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body(new MessageResponse("Access Denied!"));
        }

        return ResponseEntity.ok(orderService.getOrderHistory(id));
    }
}
