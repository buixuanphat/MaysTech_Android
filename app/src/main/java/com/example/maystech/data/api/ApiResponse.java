package com.example.maystech.data.api;

import java.util.List;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private List<T> data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getData()
    {
        return data;
    }
}
