package com.example.maystech.ui.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponses;
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
    MutableLiveData<List<Product>> products;
    MutableLiveData<List<Brand>> brands;

    public CategoryViewModel() {
        this.categoryRepository = new CategoryRepository();
        this.categories = new MutableLiveData<>();

        this.productRepository = new ProductRepository();
        this.products = new MutableLiveData<>();

        this.brandRepository = new BrandRepository();
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
        categoryRepository.getCategories(new Callback<ApiResponses<Category>>() {
            @Override
            public void onResponse(Call<ApiResponses<Category>> call, Response<ApiResponses<Category>> response) {
                if(response.isSuccessful())
                {
                    categories.setValue(response.body().getData());
                    onDataLoaded.onDataLoaded(categories.getValue().get(0).getId());
                }
            }

            @Override
            public void onFailure(Call<ApiResponses<Category>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void fetchProductOfCategory(int catId)
    {
        productRepository.getProductOfCategory(catId, new Callback<ApiResponses<Product>>() {
            @Override
            public void onResponse(Call<ApiResponses<Product>> call, Response<ApiResponses<Product>> response) {
                if(response.isSuccessful()){
                    products.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponses<Product>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetchBrandOfCategory(int catId)
    {
        brandRepository.getBrandOfCategory(catId, new Callback<ApiResponses<Brand>>() {
            @Override
            public void onResponse(Call<ApiResponses<Brand>> call, Response<ApiResponses<Brand>> response) {
                if(response.isSuccessful()){
                    brands.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponses<Brand>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void fetchProductOfCategoryAndBrand(int catId, int brandId)
    {
        productRepository.getProductOfCategoryAndBrand(catId, brandId, new Callback<ApiResponses<Product>>() {
            @Override
            public void onResponse(Call<ApiResponses<Product>> call, Response<ApiResponses<Product>> response) {
                products.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponses<Product>> call, Throwable t) {
                Log.e("error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }



}
interface OnDataLoaded{
    void onDataLoaded(int firstItem);
};
