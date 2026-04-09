package com.example.e_commerce.repository;

import java.math.BigDecimal;

public interface ProductStockView {

    Integer getProductId();
    String getProductName();
    BigDecimal getPrice();
    Integer getStock();
}