package com.example.maystech.ui.login;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.maystech.BR;

public class LoginViewModel extends BaseObservable {
    private String email;
    private String password;
    private LoginCallback loginCallback;

    public LoginViewModel(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onclickLogin()
    {
        loginCallback.loginSuccess();
    }

    public void onclickRegister()
    {
        loginCallback.register();
    }
}
