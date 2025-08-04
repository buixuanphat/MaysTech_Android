package com.example.maystech.ui.update_info;

import android.content.Context;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private final GhnRepository ghnRepository = new GhnRepository();
    private final UserRepository userRepository = new UserRepository();

    private final MutableLiveData<List<Province>> provincesList = new MutableLiveData<>();
    private final MutableLiveData<List<District>> districtsList = new MutableLiveData<>();
    private final MutableLiveData<List<Ward>> wardsList = new MutableLiveData<>();
    private final MutableLiveData<Integer> updateStatus = new MutableLiveData<>(0);
    private final MutableLiveData<User> userAfterUpdate = new MutableLiveData<>();
    private MutableLiveData<String> avatarUrl = new MutableLiveData<>();

    public LiveData<List<Province>> getProvincesList() { return provincesList; }
    public LiveData<List<District>> getDistrictsList() { return districtsList; }
    public LiveData<List<Ward>> getWardsList() { return wardsList; }
    public LiveData<Integer> getUpdateStatus() { return updateStatus; }
    public LiveData<User> getUserAfterUpdate () { return userAfterUpdate; }
    public LiveData<String> getAvatarUrl()
    {
        return this.avatarUrl;
    }


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
