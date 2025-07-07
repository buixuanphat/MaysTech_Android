package com.example.maystech.ui.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.repository.CategoryRepository;
import com.example.maystech.repository.ProductImageRepository;
import com.example.maystech.repository.ProductRepository;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    MutableLiveData<List<Category>> categories;
    MutableLiveData<List<Product>> products;
    MutableLiveData<List<Brand>> brands;

    public CategoryViewModel() {
        this.categoryRepository = new CategoryRepository();
        this.categories = new MutableLiveData<>();

        this.productRepository = new ProductRepository();
        this.products = new MutableLiveData<>();

        this.brands = new MutableLiveData<>();
    }

    public LiveData<List<Category>> getCategories()
    {
        return categories;
    }

    public LiveData<List<Product>> getProducts()
    {
        return products;
    }

    public LiveData<List<Brand>> getBrandOfCategory()
    {
        return brands;
    }

    public void fetchCategories(OnDataLoaded onDataLoaded)
    {
        categoryRepository.getCategories(new Callback<ApiResponse<Category>>() {
            @Override
            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
                if(response.isSuccessful())
                {
                    categories.setValue(response.body().getData());
                    onDataLoaded.onDataLoaded(categories.getValue().get(0).getId());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetchProducts()
    {
        productRepository.getProducts(new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if(response.isSuccessful())
                {
                    products.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetchProductOfCategory(int catId)
    {
        productRepository.getProductOfCategory(catId, new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if(response.isSuccessful()){
                    products.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetchBrandOfCategory(int catId)
    {
        productRepository.getBrandOfCategory(catId, new Callback<ApiResponse<Brand>>() {
            @Override
            public void onResponse(Call<ApiResponse<Brand>> call, Response<ApiResponse<Brand>> response) {
                if(response.isSuccessful()){
                    brands.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Brand>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetProductOfCategoryAndBrand(int catId, int brandId)
    {
        productRepository.getProductOfCategoryAndBrand(catId, brandId, new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                products.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }



}
interface OnDataLoaded{
    void onDataLoaded(int firstItem);
};
