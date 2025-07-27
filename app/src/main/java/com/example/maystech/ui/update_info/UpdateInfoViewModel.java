package com.example.maystech.ui.update_info;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.GhnApiResponse;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.User;
import com.example.maystech.data.model.Ward;
import com.example.maystech.data.repository.GhnRepository;
import com.example.maystech.data.repository.UserRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInfoViewModel extends ViewModel {

    private final GhnRepository ghnRepository = new GhnRepository();
    private final UserRepository userRepository = new UserRepository();

    private final MutableLiveData<List<Province>> provincesList = new MutableLiveData<>();
    private final MutableLiveData<List<District>> districtsList = new MutableLiveData<>();
    private final MutableLiveData<List<Ward>> wardsList = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<User> userAfterUpdate = new MutableLiveData<>();

    public LiveData<List<Province>> getProvincesList() { return provincesList; }
    public LiveData<List<District>> getDistrictsList() { return districtsList; }
    public LiveData<List<Ward>> getWardsList() { return wardsList; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<User> getUserAfterUpdate () { return userAfterUpdate; };

    // === FETCH PROVINCE ===
    public void fetchProvincesList() {
        ghnRepository.getProvince(new Callback<GhnApiResponse<Province>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<Province>> call, Response<GhnApiResponse<Province>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    provincesList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch province error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<Province>> call, Throwable t) {
                Log.e("Fetch province failure", t.getMessage());
            }
        });
    }

    public void fetchDistrictList(int provinceId) {
        ghnRepository.getDistrict(provinceId, new Callback<GhnApiResponse<District>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<District>> call, Response<GhnApiResponse<District>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    districtsList.setValue(response.body().getData());
                } else {
                    Log.e("Fetch district error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<District>> call, Throwable t) {
                Log.e("Fetch district failure", t.getMessage());
            }
        });
    }

    public void fetchWardList(int districtId) {
        ghnRepository.getWard(districtId, new Callback<GhnApiResponse<Ward>>() {
            @Override
            public void onResponse(Call<GhnApiResponse<Ward>> call, Response<GhnApiResponse<Ward>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    wardsList.setValue(response.body().getData());
                } else {
                    Log.e("Fetch ward error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<GhnApiResponse<Ward>> call, Throwable t) {
                Log.e("Fetch ward failure", t.getMessage());
            }
        });
    }

    public void updateUserInfo(String token, int userId ,JsonObject body) {

        userRepository.updateInfo(token, body, userId, new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userAfterUpdate.setValue(response.body().getData());
                    message.setValue("Cập nhật địa chỉ thành công");
                } else {
                    message.setValue("Lỗi! Cập nhật không thành công");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("Update User Info failure", t.getMessage());
                message.setValue("Không thể cập nhật: " + t.getMessage());
            }
        });
    }

}
