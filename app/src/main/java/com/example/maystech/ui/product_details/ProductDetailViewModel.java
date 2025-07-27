package com.example.maystech.ui.product_details;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.data.repository.ProductImageRepository;
import com.example.maystech.data.repository.ProductRepository;
import com.example.maystech.data.repository.UserProductRepository;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel extends ViewModel {
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    UserProductRepository userProductRepository;

    public ObservableField<String> name;
    public ObservableField<Double> price;
    public ObservableField<String> description;
    private MutableLiveData<List<ProductImage>> productImages;
    private MutableLiveData<String> addToCartMessage;
    public ProductDetailViewModel() {
        this.productRepository = new ProductRepository();
        this.productImageRepository = new ProductImageRepository();
        this.userProductRepository = new UserProductRepository();
        this.name = new ObservableField<>();
        this.price = new ObservableField<>();
        this.description = new ObservableField<>();
        productImages = new MutableLiveData<>();
        this.addToCartMessage = new MutableLiveData<>();
    }

    public LiveData<List<ProductImage>> getProductImages()
    {
        return this.productImages;
    }

    public LiveData<String> getAddToCartMessage()
    {
        return this.addToCartMessage;
    }



    public void fetchProduct(int prodId)
    {
        productRepository.getProduct(prodId, new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if(response.isSuccessful())
                {

                    Product p = response.body().getData();
                    name.set(p.getName());
                    price.set(p.getPrice());
                    description.set(p.getDescription());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


    public void fetchImageOfProduct(int prodId)
    {
        productImageRepository.getImageOfProduct(prodId, new Callback<ApiResponse<List<ProductImage>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ProductImage>>> call, Response<ApiResponse<List<ProductImage>>> response) {
                if(response.isSuccessful())
                {
                    productImages.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ProductImage>>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void addProductToCart(String token ,int userId, int prodId)
    {
        userProductRepository.addProductToCart(token , userId, prodId, new Callback<ApiResponse<ItemProductInCart>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemProductInCart>> call, Response<ApiResponse<ItemProductInCart>> response) {
                if(response.isSuccessful()) addToCartMessage.setValue("Thêm sản phẩm vào giỏ hàng thành công");
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemProductInCart>> call, Throwable t) {
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

}
