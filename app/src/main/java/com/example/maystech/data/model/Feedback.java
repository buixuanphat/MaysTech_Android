package com.example.maystech.data.model;

public class Feedback {
    private ItemProductOrder product;
    private String comment;
    private Integer rating;

    public Feedback(ItemProductOrder product, String comment, Integer rating) {
        this.product = product;
        this.comment = comment;
        this.rating = rating;
    }

    public ItemProductOrder getProduct() {
        return product;
    }

    public void setProduct(ItemProductOrder product) {
        this.product = product;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
