package com.example.maystech.data.api;

import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.utils.STATIC;
import com.example.maystech.data.model.AddToCartRequest;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.TotalCart;
import com.example.maystech.data.model.User;
import com.example.maystech.data.model.Ward;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

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
    Call<ApiResponse<List<Category>>> getCategories();




    //products
    @GET("products")
    Call<ApiResponse<List<Product>>> getProducts();

    @GET("products/category/{catId}")
    Call<ApiResponse<List<Product>>> getProductOfCategory(@Path("catId") int catId);

    @GET("products/category/{catId}/brand/{brandId}")
    Call<ApiResponse<List<Product>>> getProductOfCategoryAndBrand(@Path("catId") int catId, @Path("brandId") int brandId);


    @GET("products/{prodId}")
    Call<ApiResponse<Product>> getProduct(@Path("prodId") int prodId);




    // product-image
    @GET("product-image/{prodId}")
    Call<ApiResponse<List<ProductImage>>> getImageOfProduct(@Path("prodId") int prodId);




    // brand
    @GET("brand-category/{catId}")
    Call<ApiResponse<List<Brand>>> getBrandsOfCategory(@Path("catId") int catId);



    // === USER-PRODUCT
    @POST("user-product")
    Call<ApiResponse<ItemProductInCart>> addProductToCart(
            @Header("Authorization") String token,
            @Body AddToCartRequest request
    );

    @GET("user-product/{userId}")
    Call<ApiResponse<List<ItemProductInCart>>> getProductInCart(
            @Header("Authorization") String token,
            @Path("userId") int userId

    );

    @DELETE("user-product/{id}")
    Call<ApiResponse> deleteProductFromCart(
            @Path("id") int id,
            @Header("Authorization") String token
            );

    @GET("user-product/total/{userId}")
    Call<ApiResponse<TotalCart>> getTotalCart(
            @Path("userId") int userId,
            @Header("Authorization") String token
            );

    @PATCH("user-product/{id}")
    Call <ApiResponse> choose(
            @Path("id") int id,
            @Query("isChosen") int isChosen,
            @Header("Authorization") String token
            );


    // === GHN ===
    @GET("master-data/province")
    @Headers({"Token: "+ STATIC.TOKEN})
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

    @GET("auth/me")
    Call<ApiResponse<User>> getCurrentUser(@Header("Authorization") String token );

    @POST("auth/register")
    Call<ApiResponse<User>> register (@Body Map<String, String> body);

    @PATCH("users/{userId}")
    Call<ApiResponse<User>> updateInfo (@Header("Authorization") String token, @Body JsonObject body, @Path("userId") int userId);



    // === DELIVERY ===
    @GET("deliveries/{userId}")
    Call<ApiResponse<List<Delivery>>> getDeliveryList(@Header("Authorization") String token, @Path("userId") int userId, @Query("status") String status);

    @POST("deliveries")
    Call<ApiResponse<Delivery>> addDelivery(@Body JsonObject body);

    // === DELIVERY DETAILS ===
    @GET("delivery-details/{deliveryId}")
    Call<ApiResponse<List<ItemProductOrder>>> getProductInDelivery(@Path("deliveryId") int deliveryId);

    @POST("delivery-details/{deliveryId}")
    Call<ApiResponse<List<ItemProductOrder>>> addProductToDelivery (@Body List<ItemProductOrder> body);
}