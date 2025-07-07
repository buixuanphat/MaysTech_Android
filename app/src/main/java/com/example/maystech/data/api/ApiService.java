package com.example.maystech.data.api;

import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("categories")
    Call<ApiResponse<Category>> getCategories();

    @GET("products")
    Call<ApiResponse<Product>> getProducts();

    @GET("products/category/{catId}")
    Call<ApiResponse<Product>> getProductOfCategory(@Path("catId") int catId);

    @GET("product-image")
    Call<ApiResponse<ProductImage>> getImageByProduct();

    @GET("products/category-brand/{catId}")
    Call<ApiResponse<Brand>> getBrandsOfCategory(@Path("catId") int catId);

    @GET("products/category/{catId}/brand/{brandId}")
    Call<ApiResponse<Product>> getProductOfCategoryAndBrand(@Path("catId") int catId, @Path("brandId") int brandId);

}