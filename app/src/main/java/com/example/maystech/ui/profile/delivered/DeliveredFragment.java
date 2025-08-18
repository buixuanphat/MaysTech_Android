package com.example.maystech.ui.profile.delivered;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.data.repository.DeliveryRepository;
import com.example.maystech.databinding.FragmentDeliveredBinding;
import com.example.maystech.ui.profile.ProductOrderAdapter;
import com.example.maystech.utils.STATIC;
import com.example.maystech.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredFragment extends Fragment {

    private FragmentDeliveredBinding binding;
    private DeliveryRepository deliveryRepository;
    private DeliveryDetailsRepository deliveryDetailsRepository;
    private SharedPrefManager pref;

    private MutableLiveData<List<Delivery>> deliveryList;
    private User u;
    private String token;

    @Override
    public void onResume() {
        super.onResume();
        pref = SharedPrefManager.getInstance(requireContext());
        token = "Bearer "+ pref.getToken();
        u = pref.getUserInfo();
        fetchDeliveryOfUser(u.getId(), STATIC.DELIVERED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveredBinding.inflate(getLayoutInflater(), container, false);

        deliveryList = new MutableLiveData<>();

        deliveryRepository =  new DeliveryRepository();
        deliveryDetailsRepository = new DeliveryDetailsRepository();

        ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(requireContext());
        binding.rvDeliveredProduct.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDeliveredProduct.setAdapter(productOrderAdapter);

        deliveryList.observe(getViewLifecycleOwner(), d ->
        {
            productOrderAdapter.setData(d);
        });

          return binding.getRoot();
    }

    public void fetchDeliveryOfUser(int userId, String status)
    {
        deliveryRepository.getDeliveryOfUser(userId, STATIC.DELIVERED, new Callback<ApiResponse<List<Delivery>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Delivery>>> call, Response<ApiResponse<List<Delivery>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    deliveryList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch delivery error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Delivery>>> call, Throwable t) {
                Log.e("Fetch delivery failure", t.getMessage());
            }
        });
    }

}