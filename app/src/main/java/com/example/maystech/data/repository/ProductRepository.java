package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Product;

import java.util.List;

import retrofit2.Callback;

public class ProductRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProducts(Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.getProducts().enqueue(callback);
    }

    public void getProductOfCategory(int catId, Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.getProductOfCategory(catId).enqueue(callback);
    }

    public void getProductOfCategoryAndBrand(int catId, int brandId, Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.getProductOfCategoryAndBrand(catId, brandId).enqueue(callback);
    }

    public void getProduct(int prodId, Callback<ApiResponse<Product>> callback)
    {
        apiService.getProduct(prodId).enqueue(callback);
    }




}
