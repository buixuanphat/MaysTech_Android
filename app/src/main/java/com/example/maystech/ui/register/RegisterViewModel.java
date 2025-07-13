package com.example.maystech.ui.register;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    UserRepository userRepository;
    public ObservableField<String> email;
    public ObservableField<String> password;
    public MutableLiveData<String> message;
    public MutableLiveData<Boolean> status;

    public RegisterViewModel() {
        this.email = new ObservableField<>();
        this.password = new ObservableField<>();
        userRepository = new UserRepository();
        this.message = new MutableLiveData<>();
        this.status = new MutableLiveData<>();
    }

    public LiveData<Boolean> getStatus()
    {
        return this.status;
    }
    public LiveData<String> getMessage()
    {
        return this.message;
    }


    public void register()
    {
        Map<String, String> body = new HashMap<>();
        body.put("email", email.get());
        body.put("password", password.get());
        userRepository.register(body, new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatusCode()==201)
                    {
                        status.setValue(true);
                    }
                    message.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("error", t.getMessage());
                message.setValue(t.getMessage());
            }
        });
    }
}
