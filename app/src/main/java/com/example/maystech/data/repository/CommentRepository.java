package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Comment;
import com.example.maystech.data.model.Rating;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Callback;

public class CommentRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getComments(int prodId, Callback<ApiResponse<List<Comment>>> callback)
    {
        apiService.getComments(prodId).enqueue(callback);
    }

    public void createComment(JsonObject body, Callback<ApiResponse<Comment>> callback)
    {
        apiService.addComment(body).enqueue(callback);
    }
}
