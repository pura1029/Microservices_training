package com.synechron.springbootdemo.service;

import com.synechron.springbootdemo.model.Order;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "inventoryservice")
@Retry(name="inventoryserviceretry", fallbackMethod = "fallBackImpl")
public interface InventoryFeignClient {

    @PostMapping("/api/v1/inventory")
    ResponseEntity<Integer> updateQty();

    default ResponseEntity<Integer> fallBackImpl(Throwable exception){
        System.out.println(" Came inside the fallback implementation :: ::");
        return ResponseEntity.ok(200);
    }
}