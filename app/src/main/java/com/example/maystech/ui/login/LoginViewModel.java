package com.example.maystech.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.BR;
import com.example.maystech.data.STATIC;
import com.example.maystech.data.SharedPrefManager;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.UserRepository;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    UserRepository userRepository;
    public ObservableField<String> email;
    public ObservableField<String> password;


    public LoginViewModel() {
        this.userRepository = new UserRepository();
        this.email = new ObservableField<>();
        this.password = new ObservableField<>();
    }


    public void login (OnLogin onLogin)
    {
        JsonObject body = new JsonObject();
        body.addProperty("email", email.get());
        body.addProperty("password", password.get());

        userRepository.login(body, new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body().getStatusCode()==200)
                {
                    onLogin.onSuccess(response.body().getData());
                }

                else
                {
                    onLogin.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void fetchUserInfo(String token, OnGetCurrentUser onGetCurrentUser)
    {
        userRepository.getCurrentUser(token, new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body()!=null)
                {
                    onGetCurrentUser.onSuccess(response.body().getData());
                }
                Log.e("error", response.code() +" ");
                try {
                    Log.e("error", response.errorBody().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    interface OnLogin{
        void onSuccess(String token);
        void onFailed(String message);
    }

    interface OnGetCurrentUser
    {
        void onSuccess(User user);
        void onFailed(String message);
    }

}


