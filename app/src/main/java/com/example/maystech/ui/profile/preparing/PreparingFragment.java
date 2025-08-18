package com.example.maystech.ui.profile.preparing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.DeliveryRepository;
import com.example.maystech.databinding.FragmentPreparingBinding;
import com.example.maystech.ui.profile.ProductOrderAdapter;
import com.example.maystech.utils.STATIC;
import com.example.maystech.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparingFragment extends Fragment {

    FragmentPreparingBinding binding;
    DeliveryRepository deliveryRepository;
    MutableLiveData<List<Delivery>> deliveryList;

    @Override
    public void onResume() {
        super.onResume();
        SharedPrefManager pref = SharedPrefManager.getInstance(requireContext());
        User u = pref.getUserInfo();
        fetchDeliveryOfUser(u.getId(), STATIC.PREPARING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPreparingBinding.inflate(inflater, container, false);

        deliveryList = new MutableLiveData<>();

        deliveryRepository = new DeliveryRepository();



        ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(requireContext());
        binding.rvPreparingProduct.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPreparingProduct.setAdapter(productOrderAdapter);

        deliveryList.observe(getViewLifecycleOwner(), d ->
        {
            productOrderAdapter.setData(d);
        });

        return binding.getRoot();
    }

    void fetchDeliveryOfUser(int userId, String status) {
        deliveryRepository.getDeliveryOfUser(userId, status, new Callback<ApiResponse<List<Delivery>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Delivery>>> call, Response<ApiResponse<List<Delivery>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deliveryList.setValue(response.body().getData());
                } else {
                    Log.e("Fetch delivery error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Delivery>>> call, Throwable t) {
                Log.e("Fetch delivery failure", t.getMessage());
            }
        });
    }


}