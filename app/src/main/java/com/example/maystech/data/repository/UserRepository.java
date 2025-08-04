package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.User;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Callback;

public class UserRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void login(JsonObject body, Callback<ApiResponse<String>> callback)
    {
        apiService.login(body).enqueue(callback);
    }

    public void getCurrentUser(String token, Callback<ApiResponse<User>> callback)
    {
        apiService.getCurrentUser(token).enqueue(callback);
    }

    public void register (Map<String, String> body, Callback<ApiResponse<User>> callback)
    {
        apiService.register(body).enqueue(callback);
    }

    public void updateInfo(String token, JsonObject body, int userId, Callback<ApiResponse<User>> callback)
    {
        apiService.updateInfo(token,body, userId).enqueue(callback);
    }

    public void updateAvatar(String token, Map<String, String> body, int userId, Callback<ApiResponse<User>> callback)
    {
        apiService.updateAvatar(token, body, userId).enqueue(callback);
    }
}
