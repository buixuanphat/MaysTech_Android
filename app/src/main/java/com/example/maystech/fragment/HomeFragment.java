package com.example.maystech.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maystech.R;
import com.example.maystech.adapter.BannerAdapter;
import com.example.maystech.databinding.FragmentHomeBinding;
import com.example.maystech.model.ProductHighlight;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        List<ProductHighlight> banners = new LinkedList<>();
        banners.add(new ProductHighlight(1, "https://cdnv2.tgdd.vn/mwg-static/common/News/1577815/IMG_4120.jpeg", 1));
        banners.add(new ProductHighlight(2, "https://macstores.vn/wp-content/uploads/2024/09/lenovo-thinkpad-x1-carbon-gen-13-3.jpg", 2));

        ViewPager2 vpBanner = binding.vpProductHighlight;
        BannerAdapter bannerAdapter = new BannerAdapter(banners);
        vpBanner.setAdapter(bannerAdapter);

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