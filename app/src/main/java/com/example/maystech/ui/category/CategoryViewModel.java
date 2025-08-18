package com.example.maystech.ui.category;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.repository.BrandRepository;
import com.example.maystech.data.repository.CategoryRepository;
import com.example.maystech.data.repository.ProductRepository;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    BrandRepository brandRepository;
    MutableLiveData<List<Category>> categories;
    MutableLiveData<List<Product>> productList;
    MutableLiveData<List<Brand>> brands;

    public CategoryViewModel() {
        this.categoryRepository = new CategoryRepository();
        this.categories = new MutableLiveData<>();

        this.productRepository = new ProductRepository();
        this.productList = new MutableLiveData<>();

        this.brandRepository = new BrandRepository();
        this.brands = new MutableLiveData<>();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public LiveData<List<Brand>> getBrandOfCategory() {
        return brands;
    }

    public void fetchCategories() {
        categoryRepository.getCategories(new Callback<ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful()) {
                    categories.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void fetchProducts(@Nullable Integer categoryId, @Nullable Integer brandId)
    {
        productRepository.getProducts(categoryId , brandId , new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    productList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch products error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e("Fetch products failure", t.getMessage());
            }
        });
    }


    public void fetchBrandOfCategory(int catId) {
        brandRepository.getBrandOfCategory(catId, new Callback<ApiResponse<List<Brand>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Brand>>> call, Response<ApiResponse<List<Brand>>> response) {
                if (response.isSuccessful()) {
                    brands.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Brand>>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void searchProductByName(String kw)
    {
        productRepository.searchProductByName(kw, new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    productList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Search product error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.e("Search product failure", t.getMessage());
            }
        });
    }

}
