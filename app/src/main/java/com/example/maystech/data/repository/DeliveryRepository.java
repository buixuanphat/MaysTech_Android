package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.BestSellingProduct;
import com.example.maystech.data.model.Delivery;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Callback;

public class DeliveryRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getDeliveryOfUser(int userId, String status, Callback<ApiResponse<List<Delivery>>> callback)
    {
        apiService.getDeliveryList(userId, status).enqueue(callback);
    }

    public void getDeliveryById(int id, Callback<ApiResponse<Delivery>> callback)
    {
        apiService.getDeliveryById(id).enqueue(callback);
    }


    public void addDelivery (String token, JsonObject body, Callback<ApiResponse<Delivery>> callback)
    {
        apiService.addDelivery(token, body).enqueue(callback);
    }

    public void updateFeedbackStatus(int id, Callback<ApiResponse<Delivery>> callback)
    {
        apiService.updateFeedbackStatus(id).enqueue(callback);
    }


    public void getBestSellingProduct(Callback<ApiResponse<List<BestSellingProduct>>> callback)
    {
        apiService.getBestSellingProduct().enqueue(callback);
    }

    public void requestCancel(int id, Callback<ApiResponse<Delivery>> callback)
    {
        apiService.requestCancel(id).enqueue(callback);
    }

}
