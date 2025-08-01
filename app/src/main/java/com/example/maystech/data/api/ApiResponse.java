package com.example.maystech.data.api;

import java.util.List;

public class ApiResponse <T>{
    private int statusCode;
    private String message;
    T data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData()
    {
        return data;
    }
}
