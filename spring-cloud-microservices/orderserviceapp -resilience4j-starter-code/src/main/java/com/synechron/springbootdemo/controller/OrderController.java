package com.synechron.springbootdemo.controller;

import com.synechron.springbootdemo.model.Order;
import com.synechron.springbootdemo.model.OrderLineItem;
import com.synechron.springbootdemo.service.OrderService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Api("Order API - Version 1")
public class OrderController {

    //@Autowired
    private OrderService orderService;

    private ApplicationContext applicationContext;

    /*public OrderController(OrderService orderService){
        this.orderService = orderService;
    }*/
    @PostMapping
    public Order createOrder(@RequestBody @Valid Order order){
        //set the biderection mapping
        order.getLineItems().forEach(order::addOrderLineItem);
        /*order = new Order();
        OrderLineItem lineItem1 = new OrderLineItem();
        lineItem1.setQty(2);
        lineItem1.setPrice(25000);
        //both the links will be automatically established
        order.addOrderLineItem(lineItem1);*/
        return this.orderService.saveOrder(order);
    }

    @GetMapping
    @ApiOperation(value = "To fetch all the orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns all the Orders"),
            @ApiResponse(code = 404, message = "There are no Orders")})
    public Set<Order> fetchOrders(){
        return this.orderService.fetchOrders();
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "To fetch the order by its Order Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the Order by Id"),
            @ApiResponse(code = 404, message = "There are no Orders")})
    public Order fetchOrderById(@PathVariable @ApiParam("valid order id") long orderId){
        return this.orderService.fetchOrderByOrderId(orderId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") long orderId){
        this.orderService.archiveOrderByOrderId(orderId);
    }

    @GetMapping("/beans")
    public Set<String> getBeans(){
        return new HashSet<>(Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }


}