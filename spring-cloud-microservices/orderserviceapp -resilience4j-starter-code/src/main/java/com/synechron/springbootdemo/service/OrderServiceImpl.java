package com.synechron.springbootdemo.service;

import com.synechron.springbootdemo.dao.OrderRepository;
import com.synechron.springbootdemo.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderDAO;

    private DiscoveryClient discoveryClient;

    private RestTemplate restTemplate;

    private InventoryFeignClient inventoryFeignClient;

    @Override
    @CircuitBreaker(name = "inventoryservice", fallbackMethod = "fallBack")
    @Retry(name = "retryService", fallbackMethod = "fallBack")
    public Order saveOrder(Order order) {
        //discoveryClient();
        loadBalancedUsingRestTemplate();
        //inventoryFeignClient.updateQty();
        return this.orderDAO.save(order);
    }

    private void loadBalancedUsingRestTemplate() {
        log.info("Calling the inventory service :::: ");
        Object response = this.restTemplate.postForEntity("http://inventoryservice/api/v1/inventory", null, Object.class);
        log.info("Response :: " + ((ResponseEntity<Integer>) response).getBody());

    }

    public Order fallBack(Throwable throwable) {
        log.error("Error while inventoryservice call " + throwable.getMessage());
        return Order.builder().orderId(1111).orderDate(LocalDate.now()).totalPrice(30000).build();
    }

    private void discoveryClient() {
        List<ServiceInstance> inventoryservices = this.discoveryClient.getInstances("INVENTORYSERVICE");
        if (inventoryservices != null && inventoryservices.size() > 0) {
            //invoke the inventory service using the rest template
            String uri = inventoryservices.get(0).getUri().toString() + "/api/v1/inventory";
            log.info("URI :: " + uri);
            Object response = this.restTemplate.postForEntity(uri, null, Object.class);
            log.info("Response :: " + ((ResponseEntity<Integer>) response).getBody());
        }

    }

    @Override
    public Set<Order> fetchOrders() {
        return new HashSet<>(this.orderDAO.findAll());
    }


    @Override
    public Order fetchOrderByOrderId(long orderId) {
        return this.orderDAO.findById(orderId)
                .orElseThrow(OrderServiceImpl::invalidOrderId);
    }

    private static IllegalArgumentException invalidOrderId() {
        return new IllegalArgumentException("Invalid Order Id");
    }

    @Override
    public void archiveOrderByOrderId(long orderId) {
        this.orderDAO.deleteById(orderId);
    }

}
