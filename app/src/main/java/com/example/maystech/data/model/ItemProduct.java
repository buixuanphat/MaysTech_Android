package com.example.maystech.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ItemProduct implements Serializable {
    int id;
    private int prodId;
    private String name;
    private double price;
    private int amount;
    private String image;
    private double totalPrice;
    @SerializedName("chosen")
    private boolean isChosen;

    public ItemProduct(int id, int prodId, String name, double price, int amount, String image, double totalPrice, boolean isChosen) {
        this.id = id;
        this.prodId = prodId;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image = image;
        this.totalPrice = totalPrice;
        this.isChosen = isChosen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }
}
