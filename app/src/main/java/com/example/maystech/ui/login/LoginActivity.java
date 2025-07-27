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
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.ActivityLoginBinding;
import com.example.maystech.ui.register.RegisterActivity;

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

        Intent intent = new Intent(this, MainActivity.class);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(viewModel);
        binding.setLifecycleOwner(this);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        if(sharedPrefManager.getToken()!="")
        {
            startActivity(intent);
        }

        binding.btnLogin.setOnClickListener(v -> {
            viewModel.login(new LoginViewModel.OnLogin() {
                @Override
                public void onSuccess(String token) {
                    sharedPrefManager.saveToken(token);

                    viewModel.fetchUserInfo(token, new LoginViewModel.OnGetCurrentUser() {
                        @Override
                        public void onSuccess(User user) {
                            sharedPrefManager.saveUserInfo(user);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailed(String message) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });


        // ===CREATE ACCOUNT ===
        binding.tvRegister.setOnClickListener(v -> {
            Intent intentRegister = new Intent(this, RegisterActivity.class);
            startActivity(intentRegister);
        });
    }
}