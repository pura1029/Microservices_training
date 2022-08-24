package com.synechron.springbootdemo;

import com.synechron.springbootdemo.controller.OrderController;
import com.synechron.springbootdemo.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.time.LocalDate.now;

@SpringBootApplication
@AllArgsConstructor
@EnableSwagger2
@EnableFeignClients
public class SpringBootDemoApplication{

    private OrderController orderController;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }


    public void run(String... args) throws Exception {
        //create orders
        Order order1 = Order.builder().orderId(1001).totalPrice(20_000).orderDate(now()).build();
        Order order2 = Order.builder().orderId(1002).totalPrice(25_000).orderDate(now()).build();
        Order order3 = Order.builder().orderId(1003).totalPrice(50_000).orderDate(now()).build();
        Order order4 = Order.builder().orderId(1004).totalPrice(1_50_000).orderDate(now()).build();

        //save orders
        orderController.createOrder(order1);
        orderController.createOrder(order2);
        orderController.createOrder(order3);
        orderController.createOrder(order4);

        //fetch and print the orderIds
       // orderController.fetchOrders().stream().forEach(System.out::println);
        System.out.println("Order for Id 1001 => " + orderController.fetchOrderById(1001));
    }
}
