package com.classpath.ordersapi.controller;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Order saveOrder(@RequestBody Order order){
        log.info("Inside the save method of order {}", order);
        return this.orderService.save(order);
    }

    @GetMapping
    public Set<Order> fetchOrders(){
        log.info("Fetching all the orders ");
        return this.orderService.fetchAll();
    }

    @GetMapping("/{id}")
    public Order fetchOrderById(@PathVariable("id") long orderId){
        log.info("Fetching the order by {} ", orderId);
        return this.orderService.findByid(orderId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrderById(@PathVariable("id") long orderId){
        this.orderService.deleteOrderById(orderId);
    }
}