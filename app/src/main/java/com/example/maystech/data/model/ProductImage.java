package com.example.maystech.data.model;

public class ProductImage {
    private int id;
    private int product;
    private String image;

    public ProductImage(int id, int product, String image) {
        this.id = id;
        this.product = product;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
