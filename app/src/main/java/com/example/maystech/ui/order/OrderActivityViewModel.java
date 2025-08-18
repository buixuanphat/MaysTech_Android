package com.example.maystech.ui.order;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.data.repository.DeliveryRepository;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivityViewModel extends ViewModel {

    DeliveryRepository deliveryRepository;
    DeliveryDetailsRepository deliveryDetailsRepository;
    MutableLiveData<Delivery> deliveryLiveData;
    MutableLiveData<String> message;

    public OrderActivityViewModel() {
        this.deliveryRepository = new DeliveryRepository();
        this.deliveryDetailsRepository = new DeliveryDetailsRepository();
        this.deliveryLiveData = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
    }

    public LiveData<Delivery> getDelivery()
    {
        return this.deliveryLiveData;
    }

    public LiveData<String> getMessage()
    {
        return this.message;
    }

    public void postDelivery(String token, User user ,Delivery delivery)
    {
        JsonObject body = new JsonObject();
        body.addProperty("userId", user.getId());
        body.addProperty("phoneNumber", user.getPhoneNumber());
        body.addProperty("username", user.getUsername());
        body.addProperty("address", String.format("%s, %s, %s, %s",user.getProvince(),user.getDistrict(),user.getWard(),user.getAddressDetails()));
        body.addProperty("totalPrice", delivery.getTotalPrice());
        body.addProperty("totalAmount", delivery.getTotalAmount());

        deliveryRepository.addDelivery(token, body, new Callback<ApiResponse<Delivery>>() {
            @Override
            public void onResponse(Call<ApiResponse<Delivery>> call, Response<ApiResponse<Delivery>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    deliveryLiveData.setValue(response.body().getData());
                    message.setValue("Đặt hàng thành công");
                }
                else
                {
                    message.setValue("Lỗi! Đặt hàng không thành công");
                    try {
                        Log.e("Post delivery error", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Delivery>> call, Throwable t) {
                Log.e("Post delivery failure", t.getMessage());
                message.setValue("Lỗi! Đặt hàng không thành công");
            }
        });
    }

    public void postProductToDelivery(List<ItemProductOrder> productOrders)
    {
        deliveryDetailsRepository.addProductToDelivery(productOrders, new Callback<ApiResponse<List<ItemProductOrder>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductOrder>>> call, Response<ApiResponse<List<ItemProductOrder>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    Log.e("Post product to delivery success", response.code()+"");
                }
                else
                {
                    try {
                        Log.e("Post product to delivery error", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductOrder>>> call, Throwable t) {
                Log.e("Post product to delivery failure", t.getMessage());
            }
        });
    }
}
