package com.example.maystech.ui.product_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maystech.R;
import com.example.maystech.data.SharedPrefManager;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.ActivityMainBinding;
import com.example.maystech.databinding.ActivityProductDetailsBinding;
import com.example.maystech.ui.category.CategoryViewModel;

import java.util.List;

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
        binding.setProductDetailsViewModel(viewModel);
        binding.setLifecycleOwner(this);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        User user = sharedPrefManager.getUserInfo();
        String token = "Bearer "+ sharedPrefManager.getToken();
        int id = user.getId();

        Intent intent = getIntent();
        prodId = intent.getIntExtra("prodId", -1);

        viewModel.fetchProduct(prodId);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter();
        binding.vpImage.setAdapter(productImageAdapter);
        viewModel.fetchImageOfProduct(prodId);
        viewModel.getProductImages().observe(this, images -> {
            productImageAdapter.setData(images);
        });

        // add to cart
        binding.btnOrder.setOnClickListener(v -> {
            viewModel.addProductToCart(token, id, prodId);
        });


        viewModel.getAddToCartMessage().observe(this, message -> {
            Toast.makeText(ProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        });


    }
}