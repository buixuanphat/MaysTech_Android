package com.example.maystech.repository;

import com.example.maystech.data.api.ApiHelper;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Category;

import java.util.List;

import retrofit2.Callback;


public class CategoryRepository {
    ApiHelper apiHelper;

    public CategoryRepository() {
        apiHelper = new ApiHelper();
    }

    public void getCategories(Callback<ApiResponse<Category>> callback)
    {
        apiHelper.getCategories().enqueue(callback);
    }


}
