package com.example.e_commerce.model.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private BigDecimal price;
    private String categoryName;
}