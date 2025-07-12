package com.example.maystech.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maystech.R;
import com.example.maystech.databinding.FragmentProfileBinding;
import com.example.maystech.ui.UpdateInfo.UpdateInfoActivity;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.btnDetail.setOnClickListener(v ->
        {
            Intent intent = new Intent(requireContext(), UpdateInfoActivity.class);
            startActivity(intent);
        });


        return binding.getRoot();
    }
}