package com.example.maystech.ui.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.databinding.ItemOrderDetailBinding;
import com.example.maystech.databinding.ItemProductInOrderListBinding;
import com.example.maystech.utils.STATIC;

import java.util.ArrayList;
import java.util.List;

public class ProductInOrderListAdapter extends RecyclerView.Adapter<ProductInOrderListAdapter.ProductInOrderListViewHolder>{

    private List<ItemProductOrder> productOrderList;
    private Context context;

    public ProductInOrderListAdapter(Context context) {
        this.productOrderList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<ItemProductOrder> productOrderList)
    {
        this.productOrderList = productOrderList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductInOrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderDetailBinding binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductInOrderListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInOrderListViewHolder holder, int position) {
        ItemProductOrder item = productOrderList.get(position);
        Glide.with(context).load(item.getProductImage()).into(holder.binding.ivImage);
        holder.binding.tvName.setText(item.getProductName());
        holder.binding.tvAmountTotal.setText(String.format("Số lượng: %d", item.getTotalAmount()));
        holder.binding.tvPriceTotal.setText(STATIC.formatPrice(item.getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return this.productOrderList.size();
    }

    class ProductInOrderListViewHolder extends RecyclerView.ViewHolder
    {
        ItemOrderDetailBinding binding;
        public ProductInOrderListViewHolder(ItemOrderDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
