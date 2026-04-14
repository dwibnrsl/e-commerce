package com.example.e_commerce.model.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductHistoryResponse {

    private Integer productId;
    private String productName;
    private Integer qty;
    private String changeType;
    private String userName;
    private LocalDateTime createdAt;
}