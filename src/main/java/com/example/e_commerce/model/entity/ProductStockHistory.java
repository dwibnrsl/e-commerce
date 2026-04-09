package com.example.e_commerce.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    // getter
    public Integer getId() { return id; }
    public Integer getProductId() { return productId; }
    public Integer getUserId() { return userId; }
    public Integer getQty() { return qty; }
    public String getChangeType() { return changeType; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // setter
    public void setProductId(Integer productId) { this.productId = productId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setQty(Integer qty) { this.qty = qty; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}