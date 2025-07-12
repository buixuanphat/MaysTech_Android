package com.example.maystech.ui.UpdateInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maystech.R;
import com.example.maystech.data.model.District;
import com.example.maystech.data.model.Province;
import com.example.maystech.data.model.Ward;
import com.example.maystech.databinding.ActivityUpdateInfoBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UpdateInfoActivity extends AppCompatActivity {

    private ActivityUpdateInfoBinding binding;
    UpdateInfoViewModel viewModel;
    List<Province> provinces;
    List<District> districts;
    List<Ward> wards;

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


        // === GET PROVINCE ===
        provinces = new ArrayList<>();
        ArrayAdapter provinceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provinces);
        binding.spinnerProvince.setAdapter(provinceAdapter);
        viewModel.fetchProvincesList();
        viewModel.getProvinces().observe(this, p -> {
            provinces.clear();
            provinces.addAll(p);
            provinceAdapter.notifyDataSetChanged();
        });
        binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchDistrict(provinces.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // === GET DISTRICT ===
        districts = new ArrayList<>();
        ArrayAdapter districtAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districts);
        binding.spinnerDistrict.setAdapter(districtAdapter);
        viewModel.getDistricts().observe(this, d -> {
            districts.clear();
            districts.addAll(d);
            districtAdapter.notifyDataSetChanged();
        });
        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.fetchWard(districts.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // === GET WARD ===
        wards = new ArrayList<>();
        ArrayAdapter wardAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, wards);
        binding.spinnerWard.setAdapter(wardAdapter);
        viewModel.getWards().observe(this, w -> {
            wards.clear();
            wards.addAll(w);
            wardAdapter.notifyDataSetChanged();
        });

    }
}