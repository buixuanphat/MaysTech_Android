package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.ProductImage;

import retrofit2.Callback;


public class ProductImageRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);;
    public void getImageOfProduct(int prodId ,Callback<ApiResponses<ProductImage>> callback)
    {
        apiService.getImageOfProduct(prodId).enqueue(callback);
    }
}
