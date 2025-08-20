package com.example.maystech.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.17.34.5:8080/maystech/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
