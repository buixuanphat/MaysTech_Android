package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.ItemProductOrder;

import java.util.List;

import retrofit2.Callback;

public class DeliveryDetailsRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProductInDelivery(int deliveryId , Callback<ApiResponse<List<ItemProductOrder>>> callback)
    {
        apiService.getProductInDelivery(deliveryId).enqueue(callback);
    }

    public void addProductToDelivery(List<ItemProductOrder> body, Callback<ApiResponse<List<ItemProductOrder>>> callback)
    {
        apiService.addProductToDelivery(body).enqueue(callback);
    }

}
