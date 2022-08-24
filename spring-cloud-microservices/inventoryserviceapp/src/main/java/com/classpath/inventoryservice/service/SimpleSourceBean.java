package com.classpath.inventoryservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class SimpleSourceBean {

    @Value("${custom.message}")
    private String message;
    public String getMessage() {
        return this.message;
    }
}