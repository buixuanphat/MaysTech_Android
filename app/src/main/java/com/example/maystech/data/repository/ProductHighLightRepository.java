package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.ProductHighlight;

import java.util.List;

import retrofit2.Callback;

public class ProductHighLightRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProductHighLight(Callback<ApiResponse<List<ProductHighlight>>> callback)
    {
        apiService.getProductHighLight().enqueue(callback);
    }
}
