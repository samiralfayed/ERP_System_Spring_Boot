package com.dreisource.erp_system.repository;

import com.dreisource.erp_system.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrderId(Long orderId);
}
