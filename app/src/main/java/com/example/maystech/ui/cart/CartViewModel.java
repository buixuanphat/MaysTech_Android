package com.example.maystech.ui.cart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.repository.UserProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private UserProductRepository userProductRepository;
    private MutableLiveData<List<ItemProductInCart>> products;
    private MutableLiveData<String> addToCartMessage;
    private MutableLiveData<Delivery> delivery;

    public CartViewModel() {
        this.userProductRepository = new UserProductRepository();
        this.products = new MutableLiveData<>();
        this.addToCartMessage = new MutableLiveData<>();
        this.delivery = new MutableLiveData<>();
    }

    public LiveData<Delivery> getTotal()
    {
        return this.delivery;
    }

    public LiveData<List<ItemProductInCart>> getProducts()
    {
        return this.products;
    }

    public LiveData<String> getAddToCartMessage()
    {
        return this.addToCartMessage;
    }

    public void getProductInCart(String token ,int userId)
    {
        userProductRepository.getProductInCart(token ,userId, new Callback<ApiResponse<List<ItemProductInCart>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductInCart>>> call, Response<ApiResponse<List<ItemProductInCart>>> response) {
                if(response.isSuccessful())
                {
                    products.setValue(response.body().getData());

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductInCart>>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void addProductToCart(String token ,int userId, int catId)
    {
        userProductRepository.addProductToCart(token,userId, catId, new Callback<ApiResponse<ItemProductInCart>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemProductInCart>> call, Response<ApiResponse<ItemProductInCart>> response) {
                if (response.isSuccessful()) {
                    addToCartMessage.setValue("Thêm sản phẩm vào giỏ hàng thành công");
                    getProductInCart(token, userId);
                    getTotalCart(token ,userId);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemProductInCart>> call, Throwable t) {
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

    public void deleteProductFromCart(String token ,int id) {
        userProductRepository.deleteProductFromCart(token, id, new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    addToCartMessage.setValue("Xóa sản phẩm khỏi giỏ hàng thành công");
                    getProductInCart(token,1);
                    getTotalCart(token,1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

    public void getTotalCart(String token ,int userId)
    {
        userProductRepository.getTotalCart(token ,userId, new Callback<ApiResponse<Delivery>>() {
            @Override
            public void onResponse(Call<ApiResponse<Delivery>> call, Response<ApiResponse<Delivery>> response) {
                if(response.isSuccessful())
                {
                    delivery.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Delivery>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void choose(String token ,int id, int isChosen)
    {
        userProductRepository.choose(token , id, isChosen, new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful())
                {
                    getTotalCart(token, 1);
                    getProductInCart(token, 1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

}
