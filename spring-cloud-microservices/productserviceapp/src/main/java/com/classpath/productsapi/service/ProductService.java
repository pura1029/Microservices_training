package com.classpath.productsapi.service;

import com.classpath.productsapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

     Product save(Product product);


     Page<Product> fetchAll(Pageable pageable);


    Product fetchById(Long type);


     void deleteById(Long id);

     List<Product> filterByProductName(String productName);

}