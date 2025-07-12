package com.example.maystech.data.model;

import java.io.Serializable;

public class TotalCart implements Serializable {
    int totalAmount;
    int totalPrice;

    public TotalCart(int totalAmount, int totalPrice) {
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
