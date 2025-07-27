package com.example.maystech.ui.update_info;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.maystech.R;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.User;
import com.example.maystech.data.model.Ward;
import com.example.maystech.databinding.ActivityUpdateInfoBinding;
import com.example.maystech.ui.login.LoginActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class UpdateInfoActivity extends AppCompatActivity {

    ActivityUpdateInfoBinding binding;
    UpdateInfoViewModel viewModel;
    List<Province> provinceList;
    List<District> districtList;
    List<Ward> wardList;

    private User user;


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

        // === INIT ===

        viewModel = new UpdateInfoViewModel();

        provinceList = new ArrayList<>();
        districtList = new ArrayList<>();
        wardList = new ArrayList<>();

        user = new User();

        SharedPrefManager pref = SharedPrefManager.getInstance(this);
        user = pref.getUserInfo();
        String token = "Bearer " + pref.getToken();

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

        viewModel.getUserAfterUpdate().observe(this, u ->
        {
            pref.saveUserInfo(u);
            displayInfo(u);
        });

        viewModel.getMessage().observe(this, m ->
        {
            Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
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


        // === NÚT ĐĂNG XUẤT ===
        binding.tvLogout.setOnClickListener(v ->
        {
            pref.clearAll();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // === NÚT CẬP NHẬT ===
        binding.btnSaveInfo.setOnClickListener( v ->
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

        });
    }

    void displayInfo(User u) {
        binding.edtAddressDetails.setText(u.getAddressDetails());
        binding.edtPhoneNumber.setText(u.getPhoneNumber());
        binding.tvProvince.setText(u.getProvince());
        binding.tvDistrict.setText(u.getDistrict());
        binding.tvWard.setText(u.getWard());
    }
}