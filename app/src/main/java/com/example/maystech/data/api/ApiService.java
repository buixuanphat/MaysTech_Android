package com.example.maystech.data.api;

import androidx.annotation.Nullable;

import com.example.maystech.data.model.BestSellingProduct;
import com.example.maystech.data.model.Comment;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.model.Location;
import com.example.maystech.data.model.ProductHighlight;
import com.example.maystech.data.model.Rating;
import com.example.maystech.data.model.AddToCartRequest;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.data.model.User;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // categories
    @GET("categories")
    Call<ApiResponse<List<Category>>> getCategories();




    // === PRODUCT ===
    @GET("products")
    Call<ApiResponse<List<Product>>> getProducts(@Nullable @Query("categoryId") Integer categoryId, @Nullable @Query("brandId") Integer brandId);

    @GET("products/{prodId}")
    Call<ApiResponse<Product>> getProduct(@Path("prodId") int prodId);

    @GET("products/new")
    Call<ApiResponse<List<Product>>> getNewProduct();

    @GET("products/search")
    Call<ApiResponse<List<Product>>> searchProductByName(@Query("kw") String kw);

    @GET("products/stock/{id}")
    Call<ApiResponse<Product>> updateStock(@Path("id") int id, @Query("amount") int amount);


    // === PRODUCT-HIGHLIGHT ===
    @GET("product-highlight")
    Call<ApiResponse<List<ProductHighlight>>> getProductHighLight();


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
    Call<ApiResponse<Delivery>> getTotalCart(
            @Path("userId") int userId,
            @Header("Authorization") String token
            );

    @PATCH("user-product/{id}")
    Call <ApiResponse> choose(
            @Path("id") int id,
            @Query("isChosen") int isChosen,
            @Header("Authorization") String token
            );

    @DELETE("user-product/delete/{userId}")
    Call <ApiResponse<Void>> deleteSelected(
            @Path("userId") int userId,
            @Header("Authorization") String token
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

    @PATCH("users/avatar/{userId}")
    Call<ApiResponse<User>> updateAvatar (@Header("Authorization") String token, @Body Map<String, String> body, @Path("userId") int userId);



    // === DELIVERY ===
    @GET("deliveries/user/{userId}/{status}")
    Call<ApiResponse<List<Delivery>>> getDeliveryList(@Path("userId") int userId, @Path("status") String status);

    @GET("deliveries/{id}")
    Call<ApiResponse<Delivery>> getDeliveryById(@Path("id") int id);

    @POST("deliveries")
    Call<ApiResponse<Delivery>> addDelivery(@Header("Authorization") String token ,@Body JsonObject body);

    @PATCH("deliveries/{id}")
    Call<ApiResponse<Delivery>> updateFeedbackStatus(@Path("id") int id);

    @GET("deliveries/best-selling")
    Call<ApiResponse<List<BestSellingProduct>>> getBestSellingProduct();

    @PATCH("deliveries/cancel/request/{id}")
    Call<ApiResponse<Delivery>> requestCancel(@Path("id") int id);


    // === DELIVERY DETAILS ===
    @GET("delivery-details/{deliveryId}")
    Call<ApiResponse<List<ItemProductOrder>>> getProductInDelivery(@Path("deliveryId") int deliveryId);

    @POST("delivery-details")
    Call<ApiResponse<List<ItemProductOrder>>> addProductToDelivery ( @Body List<ItemProductOrder> body);

    // === FEEDBACK ===
    @GET("comments/{prodId}")
    Call<ApiResponse<List<Comment>>> getComments(@Path("prodId") int prodId);

    @POST("comments")
    Call<ApiResponse<Comment>> addComment(@Body JsonObject body);

    @GET("ratings/{prodId}")
    Call<ApiResponse<Double>> getRatingAvg(@Path("prodId") int prodId);

    @POST("ratings")
    Call<ApiResponse<Rating>> addRating(@Body JsonObject body);


    // === API PROVINCE ===
    @GET("https://provinces.open-api.vn/api/v1/")
    Call<List<Location>> getProvinceList();

    @GET("https://provinces.open-api.vn/api/v1/p/{provinceId}?depth=2")
    Call<JsonObject> getDistrictList(@Path("provinceId") int provinceId);

    @GET("https://provinces.open-api.vn/api/v1/d/{districtId}?depth=2")
    Call<JsonObject> getWardList(@Path("districtId") int districtId);
}