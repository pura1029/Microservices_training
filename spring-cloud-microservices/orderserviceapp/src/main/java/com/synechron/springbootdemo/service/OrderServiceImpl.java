package com.synechron.springbootdemo.service;

import com.synechron.springbootdemo.dao.OrderRepository;
import com.synechron.springbootdemo.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RefreshScope
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private DiscoveryClient discoveryClient;

    private OrderRepository orderDAO;

    private RestTemplate restTemplate;

    private InventoryFeignClient inventoryFeignClient;


    @Override
   // @CircuitBreaker(name="inventoryservice", fallbackMethod = "fallBackImpl")
    //@Retry(name = "inventoryserviceretry", fallbackMethod = "fallBackImpl")
    public Order saveOrder(Order order) {
        log.info("Inside the save Order method :: ");
        ResponseEntity<Integer> response = this.inventoryFeignClient.updateQty();
        log.info("Response through Feign client:: {}", response.getBody().toString());

        //2nd way

        //Object response = this.restTemplate.postForEntity("http://inventoryservice/api/v1/inventory", null, Object.class);
        //log.info("Response :: "+ ((ResponseEntity<Integer>)response).getBody());


        //1st way
        /* Naive implementation
        List<ServiceInstance> inventoryservices = this.discoveryClient.getInstances("inventoryservice");

        if (inventoryservices != null && inventoryservices.size() > 0 ) {
            //invoke the inventory service using the rest template
            String uri = inventoryservices.get(0).getUri().toString() +"/api/v1/inventory";
            log.info("URI :: "+ uri);
            Object response = this.restTemplate.postForEntity(uri, null, Object.class);
            log.info("Response :: "+ ((ResponseEntity<Integer>)response).getBody());

        }*/


        return this.orderDAO.save(order);
        //return Order.builder().orderId(2222).orderDate(LocalDate.now()).totalPrice(50000).build();
    }


    @Override
    public Set<Order> fetchOrders() {
        return new HashSet<>(this.orderDAO.findAll());
    }
    private Order fallBackImpl(Order order, Throwable exception){
        System.out.println(" Came inside the fallback implementation :: ::");
        return Order.builder().orderId(1111).orderDate(LocalDate.now()).totalPrice(23000).build();
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