package com.products.api.v1.product.service;

import com.products.api.v1.product.entity.Product;
import com.products.api.v1.product.repository.ProductRepository;
import com.products.common.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(long id){
        return productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product not found"));
    }

    @Override
    public void deleteById(long id){
        Product productFromDb = findById(id);
        productRepository.delete(productFromDb);
    }

    @Override
    public Product update(long id, Product product) {
        Product productFromDb = findById(id);
        BeanUtils.copyProperties(product, productFromDb);
        productFromDb.setId(id);
        return productRepository.save(productFromDb);
    }
}
