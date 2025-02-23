package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    boolean deleteProduct(Long id);
}
