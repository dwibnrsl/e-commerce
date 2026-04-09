package com.example.e_commerce.model.dto.response;

import java.math.BigDecimal;

public class ProductResponse {

    private Integer id;
    private String name;
    private BigDecimal price;

    public ProductResponse(Integer id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
}