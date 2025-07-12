package com.example.maystech.data.api;

import com.example.maystech.data.STATIC;
import com.example.maystech.data.model.AddToCartRequest;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.ItemProduct;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.TotalCart;
import com.example.maystech.data.model.User;
import com.example.maystech.data.model.Ward;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // categories
    @GET("categories")
    Call<ApiResponses<Category>> getCategories();




    //products
    @GET("products")
    Call<ApiResponses<Product>> getProducts();

    @GET("products/category/{catId}")
    Call<ApiResponses<Product>> getProductOfCategory(@Path("catId") int catId);

    @GET("products/category/{catId}/brand/{brandId}")
    Call<ApiResponses<Product>> getProductOfCategoryAndBrand(@Path("catId") int catId, @Path("brandId") int brandId);


    @GET("products/{prodId}")
    Call<ApiResponse<Product>> getProduct(@Path("prodId") int prodId);




    // product-image
    @GET("product-image/{prodId}")
    Call<ApiResponses<ProductImage>> getImageOfProduct(@Path("prodId") int prodId);




    // brand
    @GET("brand-category/{catId}")
    Call<ApiResponses<Brand>> getBrandsOfCategory(@Path("catId") int catId);



    // user-product
    @POST("user-product")
    Call<ApiResponse<ItemProduct>> addProductToCart(@Body AddToCartRequest request);

    @GET("user-product/{userId}")
    Call<ApiResponses<ItemProduct>> getProductInCart(@Path("userId") int userId);

    @DELETE("user-product/{id}")
    Call<ApiResponses> deleteProductFromCart(@Path("id") int id);

    @GET("user-product/total/{userId}")
    Call<ApiResponse<TotalCart>> getTotalCart(@Path("userId") int userId);

    @PATCH("user-product/{id}")
    Call <ApiResponse> choose(@Path("id") int id, @Query("isChosen") int isChosen);


    // === GHN ===
    @GET("master-data/province")
    @Headers({
            "Token: "+ STATIC.TOKEN
    })
    Call<GhnApiResponse<Province>> getProvince();

    @POST("master-data/district")
    Call<GhnApiResponse<District>> getDistrict(
            @Header("Token") String token,
            @Body JsonObject body
    );

    @POST("master-data/ward")
    Call<GhnApiResponse<Ward>> getWard(
            @Header("Token") String token,
            @Body JsonObject body
    );


    // === USER ===
    @POST("auth/login")
    Call<ApiResponse<String>> login(@Body JsonObject body);


}