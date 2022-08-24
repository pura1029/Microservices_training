package com.classpath.productsapi.repository;

import com.classpath.productsapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    //query DSL (Domain specific language)
    Optional<Product> findProductByNameEquals(String productName);

    @Query("select product from Product product where product.name = ?1")
    //https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#repositories.query-methods.details
    //http://localhost:8222/api/v1/products/filter?name=Zenbook
    List<Product> findByName(String productName);

}