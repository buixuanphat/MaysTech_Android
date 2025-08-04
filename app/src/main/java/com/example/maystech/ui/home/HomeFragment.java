package com.example.maystech.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maystech.databinding.FragmentHomeBinding;
import com.example.maystech.data.model.ProductHighlight;

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

        //=================================================================================
        List<ProductHighlight> banners = new LinkedList<>();
        ViewPager2 vpBanner = binding.vpBanner;
        BannerAdapter bannerAdapter = new BannerAdapter(banners);
        vpBanner.setAdapter(bannerAdapter);

        viewModel.getProductHighLightList().observe(getViewLifecycleOwner(), l ->
        {
            banners.clear();
            banners.addAll(l);
            bannerAdapter.notifyDataSetChanged();
            Log.e("error", l.get(0).getImage());
        });

        viewModel.fetchProductHighLight();


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = vpBanner.getCurrentItem();
                int nextItem = currentItem+1 == banners.size() ? 0 : currentItem+1;
                vpBanner.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        return binding.getRoot();
    }

}