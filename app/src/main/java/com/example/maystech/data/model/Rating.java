package com.example.maystech.data.model;

public class Rating {

    private int id;
    private int productId;
    private int rating;

    public Rating(int id, int productId, int rating) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
    }

    public Rating() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
