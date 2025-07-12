package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.AddToCartRequest;
import com.example.maystech.data.model.ItemProduct;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.TotalCart;

import retrofit2.Callback;

public class UserProductRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void addProductToCart(int userId, int prodId ,Callback<ApiResponse<ItemProduct>> callback)
    {
        AddToCartRequest addToCartRequest = new AddToCartRequest(userId, prodId);
        apiService.addProductToCart(addToCartRequest).enqueue(callback);
    }

    public void getProductInCart(int userId, Callback<ApiResponses<ItemProduct>> callback)
    {
        apiService.getProductInCart(userId).enqueue(callback);
    }

    public void deleteProductFromCart(int id, Callback<ApiResponses> callback)
    {
        apiService.deleteProductFromCart(id).enqueue(callback);
    }

    public void getTotalCart(int userId, Callback<ApiResponse<TotalCart>> callback)
    {
        apiService.getTotalCart(userId).enqueue(callback);
    }

    public void choose(int id, int isChosen, Callback<ApiResponse> callback )
    {
        apiService.choose(id, isChosen).enqueue(callback);
    }

}
