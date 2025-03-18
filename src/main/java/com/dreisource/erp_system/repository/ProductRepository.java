package com.dreisource.erp_system.repository;

import com.dreisource.erp_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}