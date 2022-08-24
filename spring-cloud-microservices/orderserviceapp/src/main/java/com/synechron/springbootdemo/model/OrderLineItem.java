package com.synechron.springbootdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import javax.persistence.*;
import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name="line_items")
@EqualsAndHashCode(of = "id")
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter @Getter
    private int id;

    @Setter @Getter
    private int qty;

    @Setter @Getter
    private double price;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="order_id")
    @OnDelete(action = CASCADE)
    @JsonIgnore
    @Setter
    private Order order;
}