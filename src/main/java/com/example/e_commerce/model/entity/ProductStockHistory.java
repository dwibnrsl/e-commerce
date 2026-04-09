package com.example.e_commerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_stock_history")
public class ProductStockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "user_id")
    private Integer userId;

    private Integer qty;

    @Column(name = "change_type")
    private String changeType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}