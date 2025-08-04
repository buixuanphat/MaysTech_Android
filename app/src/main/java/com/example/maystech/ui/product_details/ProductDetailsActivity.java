package com.example.maystech.ui.product_details;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maystech.R;
import com.example.maystech.data.model.Product;
import com.example.maystech.utils.STATIC;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.ActivityProductDetailsBinding;

public class ProductDetailsActivity extends AppCompatActivity {
    ProductDetailViewModel viewModel;
    ActivityProductDetailsBinding binding;
    int prodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        User user = sharedPrefManager.getUserInfo();
        String token = "Bearer "+ sharedPrefManager.getToken();
        int id = user.getId();

        Intent intent = getIntent();
        prodId = intent.getIntExtra("prodId", -1);

        //=========================================================================
        viewModel.fetchProduct(prodId);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter();
        binding.vpImage.setAdapter(productImageAdapter);
        viewModel.fetchImageOfProduct(prodId);
        viewModel.getProductImages().observe(this, images -> {
            productImageAdapter.setData(images);
        });

        binding.btnOrder.setOnClickListener(v -> {
            viewModel.addProductToCart(token, id, prodId);
        });


        viewModel.getAddToCartMessage().observe(this, message -> {
            Toast.makeText(ProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        });
        // === HIỂN THỊ THÔNG TIN SẢN PHẨM ===

        // ===  COMMENT ===
        CommentAdapter commentAdapter = new CommentAdapter(this);
        binding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvComment.setAdapter(commentAdapter);

        viewModel.fetchComments(prodId);

        viewModel.getCommentList().observe(this, commentAdapter::setData);

        // === ĐÁNH GIÁ ===
        viewModel.fetchRatingAvg(prodId);

        viewModel.getRatingAvg().observe(this, r ->
        {
            binding.ratingBar.setRating(Float.parseFloat(String.valueOf(r)));
            binding.tvRating.setText(String.valueOf(r));
        });

        viewModel.getProduct().observe(this, p ->
        {
            displayInfo(p);
        });


    }
    void displayInfo(Product p)
    {
        binding.tvName.setText(p.getName());
        if(p.getSale()==true)
        {
            binding.tvPrice.setVisibility(GONE);
            binding.tvSalePrice.setVisibility(VISIBLE);
            binding.tvOldPrice.setVisibility(VISIBLE);
            binding.tvSalePrice.setText(STATIC.formatPrice(p.getSalePrice()));
            binding.tvOldPrice.setText(STATIC.formatPrice(p.getPrice()));
            binding.tvOldPrice.setPaintFlags(binding.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else
        {
            binding.tvPrice.setText(STATIC.formatPrice(p.getPrice()));
        }
        binding.tvDescription.setText(p.getDescription());
    }

}