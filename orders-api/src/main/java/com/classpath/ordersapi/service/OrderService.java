package com.classpath.ordersapi.service;

import com.classpath.ordersapi.model.Order;
import com.classpath.ordersapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public Order save(Order order){
        log.info("Inside the save method of order service:: {} ",order);
        return  this.orderRepository.save(order);
    }

    public Set<Order> fetchAll(){
        return new HashSet<>(this.orderRepository.findAll());
    }

    public Order findByid(long orderId){
        //imperative programming
       /* Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        throw new IllegalArgumentException(("Order with the given order id is not present"));*/

        //declarative style programming
        return this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid order Id"));
    }

    public void deleteOrderById(long orderId){
        this.orderRepository.deleteById(orderId);
    }
}