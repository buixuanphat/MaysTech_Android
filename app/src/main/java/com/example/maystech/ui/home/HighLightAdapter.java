package com.example.maystech.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.databinding.ItemBannerBinding;
import com.example.maystech.data.model.ProductHighlight;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.util.List;

public class HighLightAdapter extends RecyclerView.Adapter<HighLightAdapter.BannerViewHolder> {

    private List<ProductHighlight> bannerList;

    public HighLightAdapter(List<ProductHighlight> bannerList) {
        this.bannerList = bannerList;
    }

    public void setData(List<ProductHighlight> productHighlights)
    {
        this.bannerList = productHighlights;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(bannerList.get(position).getImage()).into(holder.binding.ivImage);
        holder.binding.ivImage.setOnClickListener(v ->
        {
            Intent intent = new Intent(holder.itemView.getContext(), ProductDetailsActivity.class);
            intent.putExtra("prodId", bannerList.get(position).getProductId());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder
    {
        ItemBannerBinding binding;
        public BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
