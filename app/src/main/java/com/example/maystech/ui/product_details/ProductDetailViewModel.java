package com.example.maystech.ui.product_details;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Comment;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.Product;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.data.repository.CommentRepository;
import com.example.maystech.data.repository.ProductImageRepository;
import com.example.maystech.data.repository.ProductRepository;
import com.example.maystech.data.repository.RatingRepository;
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

    private final MutableLiveData<List<ProductImage>> productImages;
    private final MutableLiveData<String> addToCartMessage;
    private final MutableLiveData<List<Comment>> commentList;
    private final MutableLiveData<Double> ratingAvg;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final MutableLiveData<Product> product;


    public ProductDetailViewModel() {
        this.productRepository = new ProductRepository();
        this.productImageRepository = new ProductImageRepository();
        this.userProductRepository = new UserProductRepository();
        this.productImages = new MutableLiveData<>();
        this.addToCartMessage = new MutableLiveData<>();
        this.commentList = new MutableLiveData<>();
        this.ratingAvg = new MutableLiveData<>();
        this.commentRepository = new CommentRepository();
        this.ratingRepository = new RatingRepository();
        this.product = new MutableLiveData<>();
    }

    public LiveData<List<ProductImage>> getProductImages()
    {
        return this.productImages;
    }

    public LiveData<String> getAddToCartMessage()
    {
        return this.addToCartMessage;
    }

    public LiveData<List<Comment>> getCommentList()
    {
        return this.commentList;
    }

    public LiveData<Double> getRatingAvg()
    {
        return this.ratingAvg;
    }

    public LiveData<Product> getProduct()
    {
        return this.product;
    }
        public void fetchProduct(int prodId)
    {
        productRepository.getProduct(prodId, new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    product.setValue(response.body().getData());
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
                if(response.isSuccessful() && response.body()!=null)
                {
                    productImages.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch image of product error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ProductImage>>> call, Throwable t) {
                Log.e("Fetch image of product failure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void addProductToCart(String token ,int userId, int prodId)
    {
        userProductRepository.addProductToCart(token , userId, prodId, new Callback<ApiResponse<ItemProductInCart>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemProductInCart>> call, Response<ApiResponse<ItemProductInCart>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    addToCartMessage.setValue("Thêm sản phẩm vào giỏ hàng thành công");
                }
                else
                {
                    Log.e("Add procut to cart error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemProductInCart>> call, Throwable t) {
                Log.e("Add product to cart failure", t.getMessage());
                addToCartMessage.setValue(t.getMessage());
            }
        });
    }

    public void fetchComments(int prodId)
    {
        commentRepository.getComments(prodId, new Callback<ApiResponse<List<Comment>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Comment>>> call, Response<ApiResponse<List<Comment>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    commentList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("FetchComments error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Comment>>> call, Throwable t) {
                Log.e("FetchComments failure", t.getMessage());
            }
        });
    }

    public void fetchRatingAvg(int prodId)
    {
        ratingRepository.getRatings(prodId, new Callback<ApiResponse<Double>>() {
            @Override
            public void onResponse(Call<ApiResponse<Double>> call, Response<ApiResponse<Double>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    ratingAvg.setValue(response.body().getData());
                }
                else
                {
                    Log.e("FetchRatingAvg error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Double>> call, Throwable t) {
                Log.e("FetchRatingAvg failure", t.getMessage());
            }
        });
    }
}
