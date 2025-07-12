package com.example.maystech.data.repository;
import com.example.maystech.data.STATIC;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.api.GhnApiClient;
import com.example.maystech.data.api.GhnApiResponse;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.Ward;
import com.google.gson.JsonObject;

import retrofit2.Callback;

public class GhnRepository {
    ApiService apiService = GhnApiClient.getRetrofit().create(ApiService.class);

    public void getProvince(Callback<GhnApiResponse<Province>> callback)
    {
        apiService.getProvince().enqueue(callback);
    }

    public void getDistrict(int provinceId, Callback<GhnApiResponse<District>> callback)
    {
        JsonObject body =  new JsonObject();
        body.addProperty("province_id", provinceId);
        apiService.getDistrict(STATIC.TOKEN, body).enqueue(callback);
    }

    public void getWard(int districtId, Callback<GhnApiResponse<Ward>> callback)
    {
        JsonObject body = new JsonObject();
        body.addProperty("district_id", districtId);
        apiService.getWard(STATIC.TOKEN, body).enqueue(callback);
    }


}
