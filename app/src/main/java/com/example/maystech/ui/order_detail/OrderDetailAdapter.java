package com.example.maystech.ui.order_detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.databinding.ItemOrderDetailBinding;
import com.example.maystech.utils.STATIC;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewholder> {

    private List<ItemProductOrder> productList;
    private Context context;

    public OrderDetailAdapter(Context context) {
        this.productList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<ItemProductOrder> list)
    {
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderDetailBinding binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderDetailViewholder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewholder holder, int position) {
        ItemProductOrder item = productList.get(position);
        holder.binding.tvName.setText(item.getProductName());
        holder.binding.tvAmountTotal.setText("Số lượng: "+ String.valueOf(item.getTotalAmount()));
        holder.binding.tvPriceTotal.setText("Tổng: "+ STATIC.formatPrice(item.getTotalPrice()));
        Glide.with(context).load(item.getProductImage()).into(holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class OrderDetailViewholder extends RecyclerView.ViewHolder
    {
        ItemOrderDetailBinding binding;
        public OrderDetailViewholder(ItemOrderDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
