package com.synechron.springbootdemo;

import com.synechron.springbootdemo.controller.OrderController;
import com.synechron.springbootdemo.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

import static java.time.LocalDate.now;

@SpringBootApplication
@AllArgsConstructor
@EnableSwagger2
@RefreshScope
@EnableFeignClients
@EnableResourceServer
public class SpringBootDemoApplication {

    private OrderController orderController;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

/*
    public void run(ApplicationArguments args) throws Exception {
        //create orders
        Order order1 = Order.builder().totalPrice(30_000).lineItems(new HashSet<>()).orderDate(now()).build();
              //save orders
        orderController.createOrder(order1);
*/

        //fetch and print the orderIds
       // orderController.fetchOrders().stream().forEach(System.out::println);
       // System.out.println("Order for Id 1001 => " + orderController.fetchOrderById(1001));
    //}

}
