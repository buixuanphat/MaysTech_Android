package com.example.maystech.model;

public class ProductHighlight {
    private int id;
    private String image;
    private int productId;

    public ProductHighlight(int id, String image, int productId) {
        this.id = id;
        this.image = image;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
