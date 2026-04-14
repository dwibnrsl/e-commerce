package com.example.e_commerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_stock_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer qty;

    @Column(name = "change_type")
    private String changeType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}