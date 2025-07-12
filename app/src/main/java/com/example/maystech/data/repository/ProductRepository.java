package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Product;

import retrofit2.Callback;

public class ProductRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProducts(Callback<ApiResponses<Product>> callback)
    {
        apiService.getProducts().enqueue(callback);
    }

    public void getProductOfCategory(int catId, Callback<ApiResponses<Product>> callback)
    {
        apiService.getProductOfCategory(catId).enqueue(callback);
    }

    public void getProductOfCategoryAndBrand(int catId, int brandId, Callback<ApiResponses<Product>> callback)
    {
        apiService.getProductOfCategoryAndBrand(catId, brandId).enqueue(callback);
    }

    public void getProduct(int prodId, Callback<ApiResponse<Product>> callback)
    {
        apiService.getProduct(prodId).enqueue(callback);
    }




}
