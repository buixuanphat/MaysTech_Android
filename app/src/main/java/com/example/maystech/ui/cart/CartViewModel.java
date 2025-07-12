package com.example.maystech.ui.cart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiResponses;
import com.example.maystech.data.model.ItemProduct;
import com.example.maystech.data.model.TotalCart;
import com.example.maystech.data.repository.UserProductRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private UserProductRepository userProductRepository;
    private MutableLiveData<List<ItemProduct>> products;
    private MutableLiveData<String> addToCartMessage;
    private MutableLiveData<TotalCart> totalCart;

    public CartViewModel() {
        this.userProductRepository = new UserProductRepository();
        this.products = new MutableLiveData<>();
        this.addToCartMessage = new MutableLiveData<>();
        this.totalCart = new MutableLiveData<>();
    }

    public LiveData<TotalCart> getTotal()
    {
        return this.totalCart;
    }

    public LiveData<List<ItemProduct>> getProducts()
    {
        return this.products;
    }

    public LiveData<String> getAddToCartMessage()
    {
        return this.addToCartMessage;
    }

    public void getProductInCart(int userId)
    {
        userProductRepository.getProductInCart(userId, new Callback<ApiResponses<ItemProduct>>() {
            @Override
            public void onResponse(Call<ApiResponses<ItemProduct>> call, Response<ApiResponses<ItemProduct>> response) {
                if(response.isSuccessful())
                {
                    products.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponses<ItemProduct>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void addProductToCart(int userId, int catId)
    {
        userProductRepository.addProductToCart(userId, catId, new Callback<ApiResponse<ItemProduct>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemProduct>> call, Response<ApiResponse<ItemProduct>> response) {
                if (response.isSuccessful()) {
                    addToCartMessage.setValue("Thêm sản phẩm vào giỏ hàng thành công");
                    getProductInCart(userId);
                    getTotalCart(userId);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemProduct>> call, Throwable t) {
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

    public void deleteProductFromCart(int id) {
        userProductRepository.deleteProductFromCart(id, new Callback<ApiResponses>() {
            @Override
            public void onResponse(Call<ApiResponses> call, Response<ApiResponses> response) {
                if (response.isSuccessful()) {
                    addToCartMessage.setValue("Xóa sản phẩm khỏi giỏ hàng thành công");
                    getProductInCart(1);
                    getTotalCart(1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponses> call, Throwable t) {
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

    public void getTotalCart(int userId)
    {
        userProductRepository.getTotalCart(userId, new Callback<ApiResponse<TotalCart>>() {
            @Override
            public void onResponse(Call<ApiResponse<TotalCart>> call, Response<ApiResponse<TotalCart>> response) {
                if(response.isSuccessful())
                {
                    totalCart.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<TotalCart>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void choose(int id, int isChosen)
    {
        userProductRepository.choose(id, isChosen, new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful())
                {
                    getTotalCart(1);
                    getProductInCart(1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

}
