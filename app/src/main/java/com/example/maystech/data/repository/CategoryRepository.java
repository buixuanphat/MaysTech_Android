package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Category;

import java.util.List;

import retrofit2.Callback;


public class CategoryRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getCategories(Callback<ApiResponse<List<Category>>> callback)
    {
        apiService.getCategories().enqueue(callback);

    }


}
