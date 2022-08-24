package com.synechron.springbootdemo.dao;

import com.synechron.springbootdemo.model.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>{
}