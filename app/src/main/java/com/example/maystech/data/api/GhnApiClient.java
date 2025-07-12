package com.example.maystech.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GhnApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://online-gateway.ghn.vn/shiip/public-api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
