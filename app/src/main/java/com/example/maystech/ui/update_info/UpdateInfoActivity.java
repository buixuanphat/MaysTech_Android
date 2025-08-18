package com.example.maystech.ui.update_info;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
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
import com.bumptech.glide.Glide;
import com.example.maystech.R;
import com.example.maystech.data.model.Location;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.ActivityUpdateInfoBinding;
import com.example.maystech.ui.login.LoginActivity;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class UpdateInfoActivity extends AppCompatActivity {

    private ActivityUpdateInfoBinding binding;
    private UpdateInfoViewModel viewModel;
    private List<Location> provinceList;
    private List<Location> districtList;
    private List<Location> wardList;

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

            viewModel.fetchProvinceList();
        });


        // === SETUP CÁC SPINNER ===
        ArrayAdapter<Location> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceList);
        binding.spinnerProvince.setAdapter(provinceAdapter);

        viewModel.getProvinceList().observe(this, l ->
        {
            provinceList.clear();
            provinceList.addAll(l);
            provinceAdapter.notifyDataSetChanged();
        });


        ArrayAdapter<Location> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districtList);
        binding.spinnerDistrict.setAdapter(districtAdapter);

        viewModel.getDistrictList().observe(this, l ->
        {
            districtList.clear();
            districtList.addAll(l);
            districtAdapter.notifyDataSetChanged();

            viewModel.fetchWardList(l.get(0).getCode());
        });


        ArrayAdapter<Location> wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wardList);
        binding.spinnerWard.setAdapter(wardAdapter);

        viewModel.getWardList().observe(this, l -> {
            wardList.clear();
            wardList.addAll(l);
            wardAdapter.notifyDataSetChanged();
        });


        // === ONCLICK SPINNER PROVINCE ===
        binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchDistrictList(((Location) binding.spinnerProvince.getSelectedItem()).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // === ONCLICK SPINNER DISTRICT ===
        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchWardList(((Location) binding.spinnerDistrict.getSelectedItem()).getCode());
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
            if(!provinceList.isEmpty())
            {
                String phoneNumber = binding.edtPhoneNumber.getText().toString().trim();
                String addressDetails = binding.edtAddressDetails.getText().toString().trim();
                String username = binding.edtUsername.getText().toString().trim();
                Location province = (Location) binding.spinnerProvince.getSelectedItem();
                Location district = (Location) binding.spinnerDistrict.getSelectedItem();
                Location ward = (Location) binding.spinnerWard.getSelectedItem();

                if(phoneNumber.isEmpty())
                {
                    binding.edtPhoneNumber.setError("Số điện thoại không được để trống");
                }
                else if(username.length()<=3)
                {
                    binding.edtUsername.setError("Tên người dùng phải lớn hơn 3 kí tự");
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
                    binding.btnSaveInfo.setClickable(false);
                    binding.progressBar.setVisibility(VISIBLE);

                    JsonObject body = new JsonObject();
                    body.addProperty("province", province.getName());
                    body.addProperty("district", district.getName());
                    body.addProperty("ward", ward.getName());
                    body.addProperty("addressDetails", addressDetails);
                    body.addProperty("phoneNumber", phoneNumber);
                    body.addProperty("username", username);

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

            if(avatarUri == null && provinceList.isEmpty() )
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