package com.classpath.inventoryservice.conroller;

import com.classpath.inventoryservice.model.Item;
import com.classpath.inventoryservice.service.SimpleSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@Slf4j
public class InventoryController {

    private static int counter = 10_000;

    @Autowired
    private Environment environment;

    @Autowired
    private SimpleSourceBean sourceBean;

    @GetMapping
    public String fetchItemsCount(){
        return this.sourceBean.getMessage();
    }


    @PostMapping
    public ResponseEntity<Integer> updateQty(){

        log.info("Inside the Inventory service : {}", this.environment.getProperty("server.port"));
        --counter;
        return ResponseEntity.ok(counter);
    }

    @PostMapping("/items")
    public Item postItem(@RequestBody Item item){

        return item;
    }

}