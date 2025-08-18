package com.example.maystech.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.BestSellingProduct;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductHighlight;
import com.example.maystech.data.repository.DeliveryRepository;
import com.example.maystech.data.repository.ProductHighLightRepository;
import com.example.maystech.data.repository.ProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private ProductHighLightRepository productHighLightRepository;
    private MutableLiveData<List<ProductHighlight>> productHighLightList;
    private MutableLiveData<List<BestSellingProduct>> productBestSelling;
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> newProductList;
    private DeliveryRepository deliveryRepository;

    public HomeViewModel() {
        this.productHighLightRepository = new ProductHighLightRepository();
        this.productHighLightList = new MutableLiveData<>();
        this.deliveryRepository = new DeliveryRepository();
        this.productBestSelling = new MutableLiveData<>();
        this.productRepository = new ProductRepository();
        this.newProductList = new MutableLiveData<>();
    }

    public LiveData<List<ProductHighlight>> getProductHighLightList()
    {
        return this.productHighLightList;
    }

    public LiveData<List<BestSellingProduct>> getBestSellingProduct()
    {
        return this.productBestSelling;
    }

    public LiveData<List<Product>> getNewProduct()
    {
        return this.newProductList;
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


    public void fetchBestSellingProduct()
    {
        deliveryRepository.getBestSellingProduct(new Callback<ApiResponse<List<BestSellingProduct>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BestSellingProduct>>> call, Response<ApiResponse<List<BestSellingProduct>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    productBestSelling.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch bestselling product error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BestSellingProduct>>> call, Throwable t) {
                Log.e("Fetch bestselling product failure", t.getMessage());
            }
        });
    }


    public void fetchNewProduct()
    {
        productRepository.getNewProduct(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    newProductList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch new product error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e("Fetch new product failure", t.getMessage());
            }
        });
    }


}

