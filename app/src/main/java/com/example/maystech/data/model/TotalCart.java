package com.example.maystech.data.model;

import java.io.Serializable;

public class TotalCart implements Serializable {
    private int totalAmount;
    private int totalPrice;
    private double totalWeight;

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public TotalCart(int totalAmount, int totalPrice, double totalWeight) {
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
