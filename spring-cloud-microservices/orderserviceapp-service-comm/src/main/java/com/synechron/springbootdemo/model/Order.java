package com.synechron.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "orderId")
@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Order {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="order_id")
    private long orderId;

    @Max(value = 50000, message = "Order price should be more than 25k and less than 50K")
    @Min(value = 25000, message = "Order price should be more than 25k and less than 50K")
    @ApiParam("total order price")
    private double totalPrice;

    @NotNull(message = "Order date cannot be null")
    @ApiModelProperty("order date")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private Set<OrderLineItem> lineItems = new HashSet<>();

    //scaffolding method to set the bidirectional mapping
    public void addOrderLineItem(OrderLineItem orderLineItem){
        orderLineItem.setOrder(this);
        this.lineItems.add(orderLineItem);
    }

}