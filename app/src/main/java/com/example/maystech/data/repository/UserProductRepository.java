package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.AddToCartRequest;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductInCart;

import java.util.List;

import retrofit2.Callback;

public class UserProductRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void addProductToCart(String token, int userId, int prodId ,Callback<ApiResponse<ItemProductInCart>> callback)
    {
        AddToCartRequest addToCartRequest = new AddToCartRequest(userId, prodId);
        apiService.addProductToCart(token ,addToCartRequest).enqueue(callback);
    }

    public void getProductInCart(String token ,int userId, Callback<ApiResponse<List<ItemProductInCart>>> callback)
    {
        apiService.getProductInCart(token, userId).enqueue(callback);
    }

    public void deleteProductFromCart(String token ,int id, Callback<ApiResponse> callback)
    {
        apiService.deleteProductFromCart(id, token).enqueue(callback);
    }

    public void getTotalCart(String token, int userId, Callback<ApiResponse<Delivery>> callback)
    {
        apiService.getTotalCart(userId, token).enqueue(callback);
    }

    public void choose(String token ,int id, int isChosen, Callback<ApiResponse> callback )
    {
        apiService.choose(id, isChosen, token).enqueue(callback);
    }

    public void deleteSelected (int userId, String token, Callback<ApiResponse<Void>> callback)
    {
        apiService.deleteSelected(userId, token).enqueue(callback);
    }


}
