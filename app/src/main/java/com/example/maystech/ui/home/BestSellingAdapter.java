package com.example.maystech.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.BestSellingProduct;
import com.example.maystech.databinding.ItemBannerBinding;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.BannerAdapterViewHolder>{

    private List<BestSellingProduct> bestSellingProductList;
    Context context;

    public BestSellingAdapter(Context context) {
        this.bestSellingProductList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<BestSellingProduct> bestSellingProductList)
    {
        this.bestSellingProductList = bestSellingProductList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapterViewHolder holder, int position) {
        BestSellingProduct products = bestSellingProductList.get(position);
        Glide.with(context).load(products.getProductImage()).into(holder.binding.ivImage);

        holder.binding.ivImage.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("prodId", products.getProductId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return bestSellingProductList.size();
    }

    class BannerAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ItemBannerBinding binding;
        public BannerAdapterViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
