package com.example.e_commerce.dto;

import java.math.BigDecimal;

public class ProductStockDTO {

    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer stock;

    public ProductStockDTO(Integer productId, String productName, BigDecimal price, Integer stock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public Integer getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
}