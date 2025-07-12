package com.example.maystech.data.model;

public class AddToCartRequest {
    private int userId;
    private int productId;

    public AddToCartRequest(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
