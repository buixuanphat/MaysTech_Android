package com.example.maystech.ui.cart;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.ItemProduct;
import com.example.maystech.databinding.ItemProductCartBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {

    private List<ItemProduct> products;
    OnClick onClick;

    public ProductCartAdapter(OnClick onClick) {
        this.products = new ArrayList<>();
        this.onClick = onClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ItemProduct> products)
    {
        this.products = products;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductCartBinding binding = ItemProductCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder holder, int position) {
        ItemProduct itemProduct = products.get(position);
        holder.binding.tvName.setText(itemProduct.getName());

        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.binding.tvPrice.setText(formatter.format(itemProduct.getPrice()) + "đ");
        holder.binding.tvPriceTotal.setText(formatter.format(itemProduct.getTotalPrice()) + "đ");
        holder.binding.tvAmountTotal.setText(String.valueOf(itemProduct.getAmount()));
        Glide.with(holder.binding.getRoot().getContext()).load(itemProduct.getImage()).into(holder.binding.ivImage);
        holder.binding.checkBox.setChecked(itemProduct.isChosen());
        holder.binding.btnAdd.setOnClickListener(v -> {onClick.onAdd(products.get(position));});
        holder.binding.btnRemove.setOnClickListener(v ->{onClick.onRemove(products.get(position));});
        holder.binding.ivImage.setOnClickListener(v -> {onClick.onClick(products.get(position));});
        holder.binding.checkBox.setOnClickListener(v -> {onClick.onCheck(holder.binding.checkBox, products.get(position));});




    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductCartViewHolder extends RecyclerView.ViewHolder
    {
        ItemProductCartBinding binding;

        public ProductCartViewHolder(ItemProductCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface OnClick
    {
        void onAdd(ItemProduct itemProduct);
        void onRemove(ItemProduct itemProduct);
        void onClick(ItemProduct itemProduct);
        void onCheck(CheckBox cb, ItemProduct itemProduct);
    }


}
