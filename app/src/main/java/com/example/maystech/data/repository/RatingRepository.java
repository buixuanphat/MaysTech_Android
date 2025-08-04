package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Rating;
import com.google.gson.JsonObject;

import retrofit2.Callback;

public class RatingRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getRatings(int prodId, Callback<ApiResponse<Double>> callback)
    {
        apiService.getRatingAvg(prodId).enqueue(callback);
    }

    public void createRating(JsonObject body, Callback<ApiResponse<Rating>> callback)
    {
        apiService.addRating(body).enqueue(callback);
    }

}
