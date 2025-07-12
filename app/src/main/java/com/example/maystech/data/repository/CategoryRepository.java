package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Category;

import retrofit2.Callback;


public class CategoryRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getCategories(Callback<ApiResponses<Category>> callback)
    {
        apiService.getCategories().enqueue(callback);

    }


}
