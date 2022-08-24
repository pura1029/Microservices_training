package com.classpath.productsapi.service;


import com.classpath.productsapi.model.Product;
import com.classpath.productsapi.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;


    public Product save(Product product) {
        return this.productRepository.save(product);
    }


    public Page<Product> fetchAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }


    public Product fetchById(Long type) {
        return this.productRepository.findById(type).orElseThrow(() -> new IllegalArgumentException("Invalid product id passed"));
    }


    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> filterByProductName(String productName) {
        return this.productRepository.findByName(productName);
    }
}