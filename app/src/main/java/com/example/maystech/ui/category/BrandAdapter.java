package com.example.maystech.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.R;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.databinding.ItemBrandBinding;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private OnBrandClick onBrandClick;
    Context context;
    List<Brand> brands;
    int current = -1;

    public BrandAdapter(Context context, OnBrandClick onBrandClick) {
        this.onBrandClick = onBrandClick;
        this.brands = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBrandBinding binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BrandViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.binding.tvName.setText(brand.getName());

        if (position == current) {
            holder.binding.tvName.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.background.setBackgroundResource(R.drawable.shape_layout_selected);
        } else {
            holder.binding.tvName.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.binding.background.setBackgroundResource(R.drawable.shape_layout_no_selected);
        }

        holder.binding.background.setOnClickListener(v ->
        {

            current = holder.getAdapterPosition();
            notifyDataSetChanged();

            onBrandClick.onBrandClick(brand.getId());
        });


    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public void setData(List<Brand> brands) {
        current = -1;
        this.brands = brands;
        notifyDataSetChanged();
    }

    class BrandViewHolder extends RecyclerView.ViewHolder {
        ItemBrandBinding binding;

        public BrandViewHolder(ItemBrandBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface OnBrandClick {
        void onBrandClick(int brandId);
    }
}
