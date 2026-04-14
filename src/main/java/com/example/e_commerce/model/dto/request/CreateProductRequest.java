package com.example.e_commerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotNull(message = "Price tidak boleh kosong")
    @DecimalMin(value = "0.0", message = "Price minimal 0")
    private BigDecimal price;

    @NotNull(message = "Category tidak boleh kosong")
    private Integer categoryId;
}