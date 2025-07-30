package com.example.maystech.data.model;

public class Comment {
    private int id;
    private int productId;
    private String content;
    private String username;
    private String userAvatar;

    public Comment(int id, int productId, String content, String username, String userAvatar) {
        this.id = id;
        this.productId = productId;
        this.content = content;
        this.username = username;
        this.userAvatar = userAvatar;
    }

    public Comment() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
