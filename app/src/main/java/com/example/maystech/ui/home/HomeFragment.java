package com.example.maystech.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.FragmentHomeBinding;
import com.example.maystech.data.model.ProductHighlight;
import com.example.maystech.utils.SharedPrefManager;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // === HIỂN THỊ USER ===
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());
        User u = sharedPrefManager.getUserInfo();
        binding.tvUsername.setText(u.getUsername());
        Glide.with(requireContext()).load(u.getAvatar()).into(binding.ivAvatar);



        //===  SẢN PHẨM NỔI BẬT ===
        List<ProductHighlight> banners = new LinkedList<>();
        HighLightAdapter highLightAdapter = new HighLightAdapter(banners);
        binding.vpBanner.setAdapter(highLightAdapter);
        viewModel.getProductHighLightList().observe(getViewLifecycleOwner(), list ->
        {
            highLightAdapter.setData(list);
        });

        viewModel.fetchProductHighLight();





        // === SẢN PHẨM BÁN CHẠY ===
        BestSellingAdapter bestSellingProductAdapter = new BestSellingAdapter(requireContext());
        binding.vpBestSell.setAdapter(bestSellingProductAdapter);
        viewModel.getBestSellingProduct().observe(getViewLifecycleOwner(), list ->
        {
            bestSellingProductAdapter.setData(list);
        });
        viewModel.fetchBestSellingProduct();


        // === SẢN PHẨM MỚI ===
        NewProductAdapter newProductAdapter = new NewProductAdapter(requireContext());
        binding.vpNew.setAdapter(newProductAdapter);
        viewModel.getNewProduct().observe(getViewLifecycleOwner(), list ->
        {
            newProductAdapter.setData(list);
        });
        viewModel.fetchNewProduct();





        return binding.getRoot();
    }

}