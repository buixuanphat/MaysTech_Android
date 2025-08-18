package com.example.maystech.ui.update_info;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Location;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.LocationRepository;
import com.example.maystech.data.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateInfoViewModel extends ViewModel {

    private MutableLiveData<List<Location>> provinceList;
    private MutableLiveData<List<Location>> districtList;
    private MutableLiveData<List<Location>> wardList;
    private LocationRepository locationRepository;
    private UserRepository userRepository;
    private  MutableLiveData<User> userAfterUpdate;
    private MutableLiveData<String> avatarUrl;
    private MutableLiveData<Integer> updateStatus;

    public UpdateInfoViewModel() {
        this.provinceList = new MutableLiveData<>();
        this.locationRepository = new LocationRepository();
        this.wardList = new MutableLiveData<>();
        this.districtList = new MutableLiveData<>();
        this.userRepository = new UserRepository();
        this.userAfterUpdate = new MutableLiveData<>();
        this.avatarUrl =  new MutableLiveData<>();
        this.updateStatus = new MutableLiveData<>(0);
    }

    public LiveData<Integer> getUpdateStatus()
    {
        return updateStatus;
    }

    public LiveData<List<Location>> getProvinceList()
    {
        return this.provinceList;
    }

    public LiveData<List<Location>> getDistrictList()
    {
        return this.districtList;
    }

    public LiveData<List<Location>> getWardList()
    {
        return this.wardList;
    }

    public LiveData<User> getUserAfterUpdate ()
    {
        return userAfterUpdate;
    }
    public LiveData<String> getAvatarUrl()
    {
        return this.avatarUrl;
    }


    public void fetchProvinceList()
    {
        locationRepository.getProvinceList(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    provinceList.setValue(response.body());
                }
                else
                {
                    Log.e("Fetch province list error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("Fetch province list failure", t.getMessage());
            }
        });
    }


    public void fetchDistrictList(int provinceId)
    {
        locationRepository.getDistrictList(provinceId, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    JsonArray districtsJson = response.body().getAsJsonArray("districts");
                    List<Location> districts = new ArrayList<>();
                    for (JsonElement e : districtsJson) {
                        Location item = new Gson().fromJson(e, Location.class);
                        districts.add(item);
                    }
                    districtList.setValue(districts);
                }
                else
                {
                    Log.e("Fetch district list error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Fetch district list failure", t.getMessage());
            }
        });
    }


    public void fetchWardList(int districtId)
    {
        locationRepository.getWardList(districtId, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    JsonArray districtsJson = response.body().getAsJsonArray("wards");
                    List<Location> wards = new ArrayList<>();
                    for (JsonElement e : districtsJson) {
                        Location item = new Gson().fromJson(e, Location.class);
                        wards.add(item);
                    }
                    wardList.setValue(wards);
                }
                else
                {
                    Log.e("Fetch ward list error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Fetch ward list failure", t.getMessage());
            }
        });
    }

    public void updateUserInfo(String token, int userId ,JsonObject body) {

        userRepository.updateInfo(token, body, userId, new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userAfterUpdate.setValue(response.body().getData());
                    updateStatus.setValue(1);
                } else {
                    updateStatus.setValue(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("Update User Info failure", t.getMessage());
                updateStatus.setValue(-1);
            }
        });
    }

    public void updateAvatar(String token, Map<String, String> body, int userId)
    {
        userRepository.updateAvatar(token, body, userId, new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    userAfterUpdate.setValue(response.body().getData());
                    updateStatus.setValue(1);
                }
                else
                {
                    Log.e("Update avatar error", response.code()+"");
                    updateStatus.setValue(-1);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                Log.e("Update avatar failure", t.getMessage());
                updateStatus.setValue(-1);
            }
        });
    }

    public void uploadImageToCloudinary(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = getBytes(inputStream);

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "image.jpg",
                            RequestBody.create(imageBytes, MediaType.parse("image/*")))
                    .addFormDataPart("upload_preset", "avatar") // tạo trong Cloudinary
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/dnbno2tkc/image/upload")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.e("Upload", "Lỗi: " + e.getMessage());
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(responseStr);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String imageUrl = null;
                        try {
                            imageUrl = json.getString("secure_url");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        avatarUrl.postValue(imageUrl);
                    } else {
                        Log.e("Upload", "Upload fail: " + response.code());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
