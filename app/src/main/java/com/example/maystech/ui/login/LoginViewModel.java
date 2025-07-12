package com.example.maystech.ui.login;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.BR;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.UserRepository;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    UserRepository userRepository;
    MutableLiveData<Boolean> loginStatus;

    public ObservableField<String> email;
    public ObservableField<String> password;

    public LoginViewModel() {
        this.userRepository = new UserRepository();
        this.email = new ObservableField<>();
        this.password = new ObservableField<>();
        loginStatus = new MutableLiveData<>();
    }

    public LiveData<Boolean> getLoginStatus()
    {
        return loginStatus;
    }

    public void login ()
    {
        JsonObject body = new JsonObject();
        body.addProperty("email", email.get());
        body.addProperty("password", password.get());
        userRepository.login(body, new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body().getStatusCode()==200)
                {
                    loginStatus.setValue(true);
                }
                else
                {
                    loginStatus.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }



}


