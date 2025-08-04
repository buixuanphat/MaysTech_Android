package com.example.maystech.ui.update_info;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.maystech.R;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.User;
import com.example.maystech.data.model.Ward;
import com.example.maystech.databinding.ActivityUpdateInfoBinding;
import com.example.maystech.ui.login.LoginActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UpdateInfoActivity extends AppCompatActivity {

    private ActivityUpdateInfoBinding binding;
    private UpdateInfoViewModel viewModel;
    private List<Province> provinceList;
    private List<District> districtList;
    private List<Ward> wardList;

    private User user;
    private Uri avatarUri;
    int updateStatus =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityUpdateInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new UpdateInfoViewModel();

        provinceList = new ArrayList<>();
        districtList = new ArrayList<>();
        wardList = new ArrayList<>();

        user = new User();

        SharedPrefManager pref = SharedPrefManager.getInstance(this);
        user = pref.getUserInfo();
        String token = "Bearer " + pref.getToken();


        //========================================================================================================

        // === HIỂN THỊ ĐỊA CHỈ NGƯỜI DÙNG (NẾU CÓ) ===
        displayInfo(user);

        // === ẤN VÀO ĐỊA CHỈ -> MỞ FORM CẬP NHẬT ===
        binding.lnGeneralAddress.setOnClickListener(v ->
        {
            binding.lnInput.setVisibility(VISIBLE);
            binding.lnGeneralAddress.setVisibility(GONE);

            viewModel.fetchProvincesList();
        });


        // === SETUP CÁC SPINNER ===
        ArrayAdapter<Province> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceList);
        binding.spinnerProvince.setAdapter(provinceAdapter);

        viewModel.getProvincesList().observe(this, l ->
        {
            provinceList.clear();
            provinceList.addAll(l);
            provinceAdapter.notifyDataSetChanged();
        });


        ArrayAdapter<District> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districtList);
        binding.spinnerDistrict.setAdapter(districtAdapter);

        viewModel.getDistrictsList().observe(this, l ->
        {
            districtList.clear();
            districtList.addAll(l);
            districtAdapter.notifyDataSetChanged();

            viewModel.fetchWardList(l.get(0).getId());
        });


        ArrayAdapter<Ward> wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wardList);
        binding.spinnerWard.setAdapter(wardAdapter);

        viewModel.getWardsList().observe(this, l -> {
            wardList.clear();
            wardList.addAll(l);
            wardAdapter.notifyDataSetChanged();
        });


        // === ONCLICK SPINNER PROVINCE ===
        binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchDistrictList(((Province) binding.spinnerProvince.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // === ONCLICK SPINNER DISTRICT ===
        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchWardList(((District) binding.spinnerDistrict.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // === THAY ĐỔI ẢNH ĐẠI DIỆN ===
        binding.tvChangeAvatar.setOnClickListener(v->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });


        // === QUAN SÁT AVATAR URL ===
        viewModel.getAvatarUrl().observe(this, a->
        {
            Map<String, String> body =  new HashMap<>();
            body.put("avatar", a);
            viewModel.updateAvatar(token, body, user.getId());
        });


        // === ĐĂNG XUẤT ===
        binding.tvLogout.setOnClickListener(v ->
        {
            pref.clearAll();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // === LƯU THÔNG TIN ===
        binding.btnSaveInfo.setOnClickListener( v ->
        {
            binding.btnSaveInfo.setClickable(false);
            binding.progressBar.setVisibility(VISIBLE);
            if(!provinceList.isEmpty())
            {
                String phoneNumber = binding.edtPhoneNumber.getText().toString().trim();
                String addressDetails = binding.edtAddressDetails.getText().toString().trim();
                Province province = (Province) binding.spinnerProvince.getSelectedItem();
                District district = (District) binding.spinnerDistrict.getSelectedItem();
                Ward ward = (Ward) binding.spinnerWard.getSelectedItem();

                if(phoneNumber.isEmpty())
                {
                    binding.edtPhoneNumber.setError("Số điện thoại không được để trống");
                }
                else if(phoneNumber.length()!=10 )
                {
                    binding.edtPhoneNumber.setError("Số điện thoại không hợp lệ");
                }
                else if (addressDetails.isEmpty())
                {
                    binding.edtAddressDetails.setError("Địa chỉ không được để trống");
                } else if (addressDetails.length() <=3) {
                    binding.edtAddressDetails.setError("Địa chỉ phải lớn hơn 3 kí tự");
                }
                else
                {
                    JsonObject body = new JsonObject();
                    body.addProperty("province", province.getName());
                    body.addProperty("district", district.getName());
                    body.addProperty("ward", ward.getName());
                    body.addProperty("addressDetails", addressDetails);
                    body.addProperty("provinceId", province.getId());
                    body.addProperty("districtId", district.getId());
                    body.addProperty("wardId", ward.getId());
                    body.addProperty("phoneNumber", phoneNumber);

                    viewModel.updateUserInfo(token, user.getId(), body);

                    binding.lnInput.setVisibility(GONE);
                    binding.lnGeneralAddress.setVisibility(VISIBLE);
                }
            }

            if(avatarUri != null)
            {
                binding.btnSaveInfo.setClickable(false);
                binding.progressBar.setVisibility(VISIBLE);
                viewModel.uploadImageToCloudinary(this, avatarUri);
            }

            if(avatarUri == null && provinceList.isEmpty())
            {
                Toast.makeText(this, "Bạn chưa thực hiện thay đổi nào", Toast.LENGTH_SHORT).show();
            }

        });

        viewModel.getUpdateStatus().observe(this, s ->
        {
            if((avatarUri!=null && provinceList.isEmpty()) || (avatarUri==null && !provinceList.isEmpty()))
            {
                if(s==1)
                {
                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    binding.btnSaveInfo.setClickable(true);
                    binding.progressBar.setVisibility(GONE);
                }
            }
            else if(avatarUri!=null && !provinceList.isEmpty())
            {
                if(updateStatus==1 && s ==1)
                {
                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    binding.btnSaveInfo.setClickable(true);
                    binding.progressBar.setVisibility(GONE);
                }
                else if (s == -1)
                {
                    Toast.makeText(this, "Lỗi! Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
                if(s == 1)
                {
                    updateStatus = 1;
                }
            }
        });

        viewModel.getUserAfterUpdate().observe(this, u ->
        {
            pref.saveUserInfo(u);
            displayInfo(u);
        });

    }


    void displayInfo(User u) {
        binding.edtAddressDetails.setText(u.getAddressDetails());
        binding.edtPhoneNumber.setText(u.getPhoneNumber());
        binding.tvProvince.setText(u.getProvince());
        binding.tvDistrict.setText(u.getDistrict());
        binding.tvWard.setText(u.getWard());
        Glide.with(this).load(u.getAvatar()).into(binding.ivAvatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            avatarUri = data.getData();
            Glide.with(this).load(avatarUri).into(binding.ivAvatar);
        }
    }




}