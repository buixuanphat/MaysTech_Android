package com.example.maystech.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.maystech.data.model.User;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(STATIC.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveToken(String token)
    {
        editor.putString(STATIC.KEY_TOKEN, token);
        editor.commit();
    }
    public String getToken()
    {
        return sharedPreferences.getString(STATIC.KEY_TOKEN, "");
    }

    public void saveUserInfo(User user) {
        if (user == null) return;

        editor.putInt(STATIC.KEY_ID, user.getId());
        editor.putString(STATIC.KEY_EMAIL, user.getEmail());
        editor.putString(STATIC.KEY_WARD, user.getWard());
        editor.putString(STATIC.KEY_DISTRICT, user.getDistrict());
        editor.putString(STATIC.KEY_ADDRESS_DETAILS, user.getAddressDetails());
        editor.putString(STATIC.KEY_PROVINCE, user.getProvince());
        editor.putString(STATIC.KEY_PHONE_NUMBER, user.getPhoneNumber());
        editor.putString(STATIC.KEY_USERNAME, user.getUsername());
        editor.putString(STATIC.KEY_AVATAR, user.getAvatar());
        editor.commit();
    }


    public User getUserInfo() {
        User user = new User();
        user.setId(sharedPreferences.getInt(STATIC.KEY_ID, 0));
        user.setEmail(sharedPreferences.getString(STATIC.KEY_EMAIL, ""));
        user.setWard(sharedPreferences.getString(STATIC.KEY_WARD, ""));
        user.setDistrict(sharedPreferences.getString(STATIC.KEY_DISTRICT, ""));
        user.setAddressDetails(sharedPreferences.getString(STATIC.KEY_ADDRESS_DETAILS, ""));
        user.setProvince(sharedPreferences.getString(STATIC.KEY_PROVINCE, ""));
        user.setPhoneNumber(sharedPreferences.getString(STATIC.KEY_PHONE_NUMBER, ""));
        user.setUsername(sharedPreferences.getString(STATIC.KEY_USERNAME, ""));
        user.setAvatar(sharedPreferences.getString(STATIC.KEY_AVATAR, ""));
        return user;
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }


}
