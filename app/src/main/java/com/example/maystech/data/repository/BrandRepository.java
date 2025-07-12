package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Brand;

import retrofit2.Callback;

public class BrandRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
    public void getBrandOfCategory(int catId, Callback<ApiResponses<Brand>> callback)
    {
        apiService.getBrandsOfCategory(catId).enqueue(callback);
    }

}

