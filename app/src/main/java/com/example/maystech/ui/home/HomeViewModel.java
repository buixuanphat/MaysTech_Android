package com.example.maystech.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.ProductHighlight;
import com.example.maystech.data.repository.ProductHighLightRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private ProductHighLightRepository productHighLightRepository;
    private MutableLiveData<List<ProductHighlight>> productHighLightList;

    public HomeViewModel() {
        this.productHighLightRepository = new ProductHighLightRepository();
        this.productHighLightList = new MutableLiveData<>();
    }

    public LiveData<List<ProductHighlight>> getProductHighLightList()
    {
        return this.productHighLightList;
    }


    public void fetchProductHighLight()
    {
        productHighLightRepository.getProductHighLight(new Callback<ApiResponse<List<ProductHighlight>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ProductHighlight>>> call, Response<ApiResponse<List<ProductHighlight>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    productHighLightList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch product highlight error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ProductHighlight>>> call, Throwable t) {
                Log.e("Fetch product highlight failure", t.getMessage());
            }
        });
    }
}
