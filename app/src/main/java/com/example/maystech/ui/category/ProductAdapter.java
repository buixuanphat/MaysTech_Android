package com.example.maystech.ui.category;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.R;
import com.example.maystech.data.model.Product;
import com.example.maystech.databinding.ItemProductBinding;
import com.example.maystech.ui.product_details.ProductDetailsActivity;
import com.example.maystech.utils.STATIC;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    Context context;

    public ProductAdapter(Context context) {
        this.productList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductAdapter.ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(holder.getAdapterPosition());
        Glide.with(context).load(product.getImageUrl()).into(holder.binding.ivImage);
        holder.binding.tvName.setText(product.getName());
        if(product.isActive())
        {
            holder.binding.tvName.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.black));
            if(product.getSalePrice()==-1)
            {
                holder.binding.tvPrice.setVisibility(VISIBLE);
                holder.binding.tvPrice.setText(STATIC.formatPrice(product.getPrice()));
                holder.binding.tvSalePrice.setVisibility(GONE);
            }
            else
            {
                holder.binding.tvPrice.setVisibility(GONE);
                holder.binding.tvSalePrice.setVisibility(VISIBLE);
                holder.binding.tvSalePrice.setText(STATIC.formatPrice(product.getSalePrice()));
            }
        }
        else
        {
            holder.binding.tvPrice.setVisibility(VISIBLE);
            holder.binding.tvSalePrice.setVisibility(GONE);

            holder.binding.tvPrice.setText("Ngừng kinh doanh");
            holder.binding.tvName.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
            holder.binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));

        }

        holder.binding.ivImage.setOnClickListener(v->
        {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("prodId" ,product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setData(List<Product> productsList) {
        this.productList.clear();
        this.productList.addAll(productsList);
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
