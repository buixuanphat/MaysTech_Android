package com.example.maystech.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.Product;
import com.example.maystech.databinding.ItemBannerBinding;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductViewHolder> {

    List<Product> newProductList;
    Context context;

    public NewProductAdapter(Context context) {
        this.newProductList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Product> newProductList)
    {
        this.newProductList = newProductList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHolder holder, int position) {
        Product product = newProductList.get(position);
        Glide.with(context).load(product.getImageUrl()).into(holder.binding.ivImage);

        holder.binding.ivImage.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("prodId", product.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    class NewProductViewHolder extends RecyclerView.ViewHolder
    {
        ItemBannerBinding binding;
        public NewProductViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
