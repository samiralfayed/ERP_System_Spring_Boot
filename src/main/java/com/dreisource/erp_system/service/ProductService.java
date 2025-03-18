package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.Product;
import java.util.List;

public interface ProductService {

    Product findById(Long id);  // ✅ Add this method to the interface

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Product product);

    boolean deleteProduct(Long id);
}
