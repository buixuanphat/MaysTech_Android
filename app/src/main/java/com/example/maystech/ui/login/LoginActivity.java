package com.example.maystech.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.maystech.MainActivity;
import com.example.maystech.R;
import com.example.maystech.databinding.ActivityLoginBinding;
import com.example.maystech.ui.product_details.ProductDetailViewModel;
import com.example.maystech.ui.register.RegisterActivity;
import com.example.maystech.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity{

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(viewModel);
        binding.setLifecycleOwner(this);


        binding.btnLogin.setOnClickListener(v -> {
            viewModel.login();
        });

        viewModel.getLoginStatus().observe(this, s -> {
            if(s)
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }
}