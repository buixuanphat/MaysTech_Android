package com.example.maystech.data.repository;

import androidx.annotation.Nullable;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Query;

public class ProductRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProducts(@Nullable Integer categoryId, Integer brandId , Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.getProducts(categoryId, brandId).enqueue(callback);
    }

    public void getProduct(int prodId, Callback<ApiResponse<Product>> callback)
    {
        apiService.getProduct(prodId).enqueue(callback);
    }

    public void getNewProduct(Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.getNewProduct().enqueue(callback);
    }

    public void searchProductByName(String kw, Callback<ApiResponse<List<Product>>> callback)
    {
        apiService.searchProductByName(kw).enqueue(callback);
    }


}
