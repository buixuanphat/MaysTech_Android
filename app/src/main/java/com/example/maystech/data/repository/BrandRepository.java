package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Brand;

import java.util.List;

import retrofit2.Callback;

public class BrandRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
    public void getBrandOfCategory(int catId, Callback<ApiResponse<List<Brand>>> callback)
    {
        apiService.getBrandsOfCategory(catId).enqueue(callback);
    }

}

