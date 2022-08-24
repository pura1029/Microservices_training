package com.classpath.productsapi.controller;

import com.classpath.productsapi.model.Product;
import com.classpath.productsapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(CREATED)

    public Product save(@RequestBody @Valid Product product){
        return (Product) this.productService.save(product);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> fetchAllProducts(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            ){
        Pageable pagingRequest = PageRequest.of(page, size, Sort.by("price").ascending());
        Page<Product> pageProducts = this.productService.fetchAll(pagingRequest);

        //create a response object
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", pageProducts.getTotalPages());
        response.put("totalElements", pageProducts.getTotalElements());
        response.put("products", pageProducts.getContent());

        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity(response, OK);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public Product fetchById(@PathVariable("id") long productId){
        return  this.productService.fetchById(productId);
    }

    @GetMapping("/filter")
    public List<Product> findProductsByName(@RequestParam("name") String productName){
        return  this.productService.filterByProductName(productName);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable ("id")long productId){
        this.productService.deleteById(productId);
    }
}