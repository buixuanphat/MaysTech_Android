package com.example.maystech.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.5:8080/maystech/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
