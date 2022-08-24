package com.classpath.productsapi.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@ToString(exclude = "orderLineItem")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {

    @Id
    @Getter
    @Setter
    private long id;

    @Setter
    @Getter
    @Min(value = 15000, message = "The product price should be greater than 15k")
    @Max(value = 500000, message = "The product price should be less than 500k")
    private double price;

    @Setter
    @Getter
    @NotEmpty(message = "Product name cannot be empty")
    private String name;

  /*  @OneToOne
    @JoinColumn(name="order_line_item_id", nullable = false)
    @Setter
    private OrderLineItem orderLineItem;
    */
}