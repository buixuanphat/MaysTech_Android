package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.User;
import com.google.gson.JsonObject;

import retrofit2.Callback;

public class UserRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void login(JsonObject body, Callback<ApiResponse<String>> callback)
    {
        apiService.login(body).enqueue(callback);
    }

}
