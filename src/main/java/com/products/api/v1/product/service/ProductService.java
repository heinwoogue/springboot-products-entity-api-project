package com.products.api.v1.product.service;

import com.products.api.v1.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(Product product);

    Page<Product> findAll(Pageable pageable);

    Product findById(long id);

    void deleteById(long id);

    Product update(long id, Product product);
}
