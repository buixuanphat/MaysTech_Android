package com.example.maystech.ui.feedback;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Comment;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.model.Rating;
import com.example.maystech.data.repository.CommentRepository;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.data.repository.DeliveryRepository;
import com.example.maystech.data.repository.RatingRepository;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackViewModel extends ViewModel {

    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final MutableLiveData<List<ItemProductOrder>> productList;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final MutableLiveData<Integer> taskSuccess;
    DeliveryRepository deliveryRepository;

    public FeedbackViewModel() {
        this.deliveryDetailsRepository = new DeliveryDetailsRepository();
        this.productList = new MutableLiveData<>();
        commentRepository = new CommentRepository();
        ratingRepository = new RatingRepository();
        taskSuccess = new MutableLiveData<>(0);
        deliveryRepository = new DeliveryRepository();
    }

    public LiveData<Integer> getTaskSuccess() {
        return this.taskSuccess;
    }

    public LiveData<List<ItemProductOrder>> getProductList() {
        return this.productList;
    }


    public void fetchListProduct(int deliveryId) {
        deliveryDetailsRepository.getProductInDelivery(deliveryId, new Callback<ApiResponse<List<ItemProductOrder>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductOrder>>> call, Response<ApiResponse<List<ItemProductOrder>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.setValue(response.body().getData());
                } else {
                    Log.e("Fetch product in delivery error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductOrder>>> call, Throwable t) {
                Log.e("Fetch product in delivery failure", t.getMessage());
            }
        });
    }

    public void createComments(int userId, Comment comment) {
        JsonObject body = new JsonObject();
        body.addProperty("userId", userId);
        body.addProperty("productId", comment.getProductId());
        body.addProperty("content", comment.getContent());
        commentRepository.createComment(body, new Callback<ApiResponse<Comment>>() {
            @Override
            public void onResponse(Call<ApiResponse<Comment>> call, Response<ApiResponse<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskSuccess.setValue(taskSuccess.getValue() + 1);
                } else {
                    taskSuccess.setValue(-1);
                    Log.e("Create comment error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Comment>> call, Throwable t) {
                Log.e("Create comment failure", t.getMessage());
                taskSuccess.setValue(-1);
            }
        });
    }

    public void createRating(int userId, Rating rating) {
        JsonObject body = new JsonObject();
        body.addProperty("userId", userId);
        body.addProperty("productId", rating.getProductId());
        body.addProperty("rating", rating.getRating());

        ratingRepository.createRating(body, new Callback<ApiResponse<Rating>>() {
            @Override
            public void onResponse(Call<ApiResponse<Rating>> call, Response<ApiResponse<Rating>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskSuccess.setValue(taskSuccess.getValue() + 1);
                } else {
                    taskSuccess.setValue(-1);
                    Log.e("Create rating error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Rating>> call, Throwable t) {
                Log.e("Create rating failure", t.getMessage());
                taskSuccess.setValue(-1);
            }
        });
    }

    public void updateFeedbackStatus(int id) {
        deliveryRepository.updateFeedbackStatus(id, new Callback<ApiResponse<Delivery>>() {
            @Override
            public void onResponse(Call<ApiResponse<Delivery>> call, Response<ApiResponse<Delivery>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskSuccess.setValue(taskSuccess.getValue() + 1);
                } else {
                    taskSuccess.setValue(-1);
                    Log.e("Update feedback status error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Delivery>> call, Throwable t) {
                Log.e("Update feedback status failure", t.getMessage());
                taskSuccess.setValue(-1);
            }
        });
    }

}
