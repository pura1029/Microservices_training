package com.synechron.springbootdemo.dao;

import com.synechron.springbootdemo.model.Order;

import java.util.Optional;
import java.util.Set;

public interface OrderDAO {

    Order saveOrder(Order order);

    Set<Order> fetchOrders();

    Optional<Order> fetchOrderByOrderId(long orderId);

    void archiveOrderByOrderId(long orderId);
}