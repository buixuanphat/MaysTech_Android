package com.example.maystech.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maystech.MainActivity;
import com.example.maystech.R;
import com.example.maystech.databinding.ActivityLoginBinding;
import com.example.maystech.ui.register.RegisterActivity;
import com.example.maystech.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private LoginViewModel loginViewModel;

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

        loginViewModel = new LoginViewModel(this);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginFailed() {
        Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
        startActivity(intent);
    }

    @Override
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}