package com.example.maystech.ui.profile;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.databinding.ItemProductInOrderListBinding;
import com.example.maystech.ui.feedback.FeedbackActivity;
import com.example.maystech.ui.order_detail.OrderDetailActivity;
import com.example.maystech.utils.STATIC;

import java.util.ArrayList;
import java.util.List;


public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductOrderViewHolder> {

    private List<Delivery> deliveryList;
    private Context context;

    public ProductOrderAdapter(Context context) {
        this.deliveryList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ProductOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductInOrderListBinding binding = ItemProductInOrderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductOrderViewHolder(binding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProductOrderViewHolder holder, int position) {
       Delivery delivery = deliveryList.get(position);
       Glide.with(context).load(delivery.getProductImage()).into(holder.binding.ivImage);
       holder.binding.tvName.setText(delivery.getProductName());
       holder.binding.tvAmountTotal.setText("Số lượng: "+ String.valueOf(delivery.getTotalAmount()));
       holder.binding.tvPriceTotal.setText("Tổng: "+ STATIC.formatPrice(delivery.getTotalPrice()));
       holder.binding.tvTotalOrder.setText(STATIC.formatPrice(delivery.getTotalPrice()));

       holder.binding.btnMore.setOnClickListener(v ->
       {
           Intent intent = new Intent(context, OrderDetailActivity.class);
           intent.putExtra("deliveryId", delivery.getId());
           context.startActivity(intent);
       });

       if(!delivery.getHasFeedback() && delivery.getStatus().equals(STATIC.DELIVERED) )
       {
           holder.binding.btnFeedback.setVisibility(VISIBLE);
       }
       else
       {
           holder.binding.btnFeedback.setVisibility(GONE);
       }

       holder.binding.btnFeedback.setOnClickListener(v ->
       {
           Intent intent = new Intent(context, FeedbackActivity.class);
           intent.putExtra("deliveryId", delivery.getId());
           context.startActivity(intent);
       });


    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public void setData(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
        notifyDataSetChanged();
    }


    class ProductOrderViewHolder extends RecyclerView.ViewHolder {
        ItemProductInOrderListBinding binding;

        public ProductOrderViewHolder(ItemProductInOrderListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
