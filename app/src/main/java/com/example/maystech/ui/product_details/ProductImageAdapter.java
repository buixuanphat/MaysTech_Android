package com.example.maystech.ui.product_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.ProductImage;
import com.example.maystech.databinding.ItemImageProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>{

    private List<ProductImage> productImages;

    public ProductImageAdapter() {
        this.productImages = new ArrayList<>();
    }

    public void setData(List<ProductImage> productImages)
    {
        this.productImages = productImages;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageProductBinding binding = ItemImageProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(productImages.get(position).getImage()).into(holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    class ProductImageViewHolder extends RecyclerView.ViewHolder
    {
        ItemImageProductBinding binding;
        public ProductImageViewHolder(ItemImageProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
