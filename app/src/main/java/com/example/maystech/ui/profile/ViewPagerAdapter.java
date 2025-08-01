package com.example.maystech.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.maystech.ui.profile.cancelled.CancelledFragment;
import com.example.maystech.ui.profile.delivered.DeliveredFragment;
import com.example.maystech.ui.profile.preparing.PreparingFragment;
import com.example.maystech.ui.profile.shipping.ShippingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new PreparingFragment();
            case 1 : return new ShippingFragment();
            case 2 : return new DeliveredFragment();
            case 3 : return new CancelledFragment();
            default: return new PreparingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
