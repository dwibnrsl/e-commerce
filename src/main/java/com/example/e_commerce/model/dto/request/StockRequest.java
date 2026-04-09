package com.example.e_commerce.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    @NotNull(message = "Qty tidak boleh kosong")
    @Min(value = 1, message = "Qty minimal 1")
    private Integer qty;

    @NotNull(message = "User tidak boleh kosong")
    private Integer userId;
}