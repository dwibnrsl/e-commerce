package com.example.e_commerce.model.dto.response;

public class StockResponse {

    private String message;

    public StockResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}