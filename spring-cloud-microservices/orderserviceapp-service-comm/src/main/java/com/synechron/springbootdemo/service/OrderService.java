package com.synechron.springbootdemo.service;

import com.synechron.springbootdemo.model.Order;
import java.util.Set;

public interface OrderService {

    Order saveOrder(Order order);

    Set<Order> fetchOrders();

    Order fetchOrderByOrderId(long orderId);

    void archiveOrderByOrderId(long orderId);

}