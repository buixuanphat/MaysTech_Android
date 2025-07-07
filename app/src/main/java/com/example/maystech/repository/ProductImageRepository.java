package com.example.maystech.repository;

import com.example.maystech.data.api.ApiHelper;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.ProductImage;

import retrofit2.Callback;

public class ProductImageRepository {
    private ApiHelper apiHelper;
    public ProductImageRepository() {
        this.apiHelper = new ApiHelper();
    }

    public void getImageByProduct (Callback<ApiResponse<ProductImage>> callback)
    {
        apiHelper.getImageByProduct().enqueue(callback);
    }
}
