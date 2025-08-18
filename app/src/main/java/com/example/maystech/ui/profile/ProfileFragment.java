package com.example.maystech.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.User;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.databinding.FragmentProfileBinding;
import com.example.maystech.ui.update_info.UpdateInfoActivity;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());
        User u = sharedPrefManager.getUserInfo();
        Glide.with(requireContext()).load(u.getAvatar()).into(binding.ivAvatar);
        binding.tvUsername.setText(u.getUsername());

        binding.btnDetail.setOnClickListener(v ->
        {
            Intent intent = new Intent(requireContext(), UpdateInfoActivity.class);
            startActivity(intent);
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());
        binding.vpOrderStatus.setAdapter(adapter);

        new TabLayoutMediator(binding.tlTabLayout, binding.vpOrderStatus, (tab, position) ->
        {
            switch (position) {
                case 0:
                    tab.setText("Đang chuẩn bị");
                    break;
                case 1:
                    tab.setText("Đang vận chuyển");
                    break;
                case 2:
                    tab.setText("Đã nhận");
                    break;
                case 3:
                    tab.setText("Đã huỷ");
                    break;
            }
        }).attach();


        return binding.getRoot();
    }

}