package com.example.e_commerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StockRequest {

    @NotNull(message = "Qty tidak boleh kosong")
    @Min(value = 1, message = "Qty minimal 1")
    private Integer qty;

    @NotNull(message = "User tidak boleh kosong")
    private Integer userId;

    public Integer getQty() { 
        return qty; 
    }
    public Integer getUserId() { 
        return userId; 
    }

    public void setQty(Integer qty) { 
        this.qty = qty; 
    }
    public void setUserId(Integer userId) { 
        this.userId = userId; 
    }
}