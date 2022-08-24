package com.classpath.inventoryservice.model;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
public class Item {
    private long id;
    private String name;
    private double price;
}