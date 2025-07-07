package com.example.maystech.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.databinding.ItemBrandBinding;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder>{

    private OnBrandClick onBrandClick;
    List<Brand> brands;

    public BrandAdapter(OnBrandClick onBrandClick) {
        this.onBrandClick = onBrandClick;
        this.brands = new ArrayList<>();
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBrandBinding binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new BrandViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        holder.bind(brands.get(position));
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public void setData(List<Brand> brands) {
        this.brands = brands;
        notifyDataSetChanged();
    }

    class BrandViewHolder extends RecyclerView.ViewHolder
    {
        ItemBrandBinding binding;
        public BrandViewHolder(ItemBrandBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }

        public void bind(Brand brand) {
            binding.tvName.setText(brand.getName());
            binding.getRoot().setOnClickListener(v -> {
                onBrandClick.onBrandClick(brand);
            });
        }
    }

    interface OnBrandClick
    {
        void onBrandClick(Brand brand);
    }
}
