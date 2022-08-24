package com.synechron.springbootdemo.dao;

import com.synechron.springbootdemo.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static Set<Order> orders = new HashSet<>();

    @Override
    public Order saveOrder(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public Set<Order> fetchOrders() {
        return orders;
    }

    @Override
    public Optional<Order> fetchOrderByOrderId(long orderId) {
        return  orders.stream().filter(order ->  order.getOrderId() == orderId).findFirst();
    }

    @Override
    public void archiveOrderByOrderId(long orderId) {
        orders.removeIf(order -> order.getOrderId() == orderId);
    }
}