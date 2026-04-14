package com.example.e_commerce.model.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductStockResponse {

    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer stock;
}