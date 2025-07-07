package com.example.maystech.data.api;

import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;

import java.util.List;

import retrofit2.Call;

public class ApiHelper {
    private ApiService apiService;

    public ApiHelper() {
        this.apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public Call<ApiResponse<Category>> getCategories()
    {
        return apiService.getCategories();
    }

    public Call<ApiResponse<Product>> getProducts()
    {
        return apiService.getProducts();
    }

    public Call<ApiResponse<Product>> getProductOfCategory(int catId)
    {
        return apiService.getProductOfCategory(catId);
    }

    public Call<ApiResponse<ProductImage>> getImageByProduct()
    {
        return apiService.getImageByProduct();
    }

    public Call<ApiResponse<Brand>> getBrandOfCategory(int catId)
    {
        return apiService.getBrandsOfCategory(catId);
    }

    public Call<ApiResponse<Product>> getProductOfCategoryAndBrand(int catId, int brandId)
    {
        return apiService.getProductOfCategoryAndBrand(catId, brandId);
    }
}
