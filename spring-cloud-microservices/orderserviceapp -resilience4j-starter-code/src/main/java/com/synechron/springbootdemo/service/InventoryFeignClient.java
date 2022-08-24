package com.synechron.springbootdemo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "INVENTORYSERVICE")
public interface InventoryFeignClient {
    @PostMapping("/api/v1/inventory")
    ResponseEntity<Integer> updateQty();

}