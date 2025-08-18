package com.example.maystech.data.repository;

import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.model.Location;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Callback;

public class LocationRepository {
    ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    public void getProvinceList(Callback<List<Location>> callback)
    {
        apiService.getProvinceList().enqueue(callback);
    }

    public void getDistrictList(int provinceId, Callback<JsonObject> callback)
    {
        apiService.getDistrictList(provinceId).enqueue(callback);
    }

    public void getWardList(int districtId, Callback<JsonObject> callback)
    {
        apiService.getWardList(districtId).enqueue(callback);
    }
}
