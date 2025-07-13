package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.ProductImage;

import java.util.List;

import retrofit2.Callback;


public class ProductImageRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);;
    public void getImageOfProduct(int prodId ,Callback<ApiResponse<List<ProductImage>>> callback)
    {
        apiService.getImageOfProduct(prodId).enqueue(callback);
    }
}
