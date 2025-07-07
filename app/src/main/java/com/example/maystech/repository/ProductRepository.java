package com.example.maystech.repository;

import com.example.maystech.data.api.ApiHelper;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;

import retrofit2.Callback;

public class ProductRepository {
    ApiHelper apiHelper;

    public ProductRepository() {
        apiHelper = new ApiHelper();
    }

    public void getProducts(Callback<ApiResponse<Product>> callback)
    {
        apiHelper.getProducts().enqueue(callback);
    }

    public void getProductOfCategory(int catId, Callback<ApiResponse<Product>> callback)
    {
        apiHelper.getProductOfCategory(catId).enqueue(callback);
    }

    public void getBrandOfCategory(int catId, Callback<ApiResponse<Brand>> callback)
    {
        apiHelper.getBrandOfCategory(catId).enqueue(callback);
    }
    public void getProductOfCategoryAndBrand(int catId, int brandId, Callback<ApiResponse<Product>> callback)
    {
        apiHelper.getProductOfCategoryAndBrand(catId, brandId).enqueue(callback);
    }



}
