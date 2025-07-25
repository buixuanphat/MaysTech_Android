package com.example.maystech.ui.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.Product;
import com.example.maystech.databinding.ItemProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnClickProduct onClickProduct;

    public ProductAdapter(OnClickProduct onClickProduct) {
        this.products = new ArrayList<>();
        this.onClickProduct = onClickProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductAdapter.ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setData(List<Product> productsList) {
        products = productsList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Product p) {
            Glide.with(binding.getRoot().getContext()).load(p.getImage()).into(binding.ivImage);
            binding.tvName.setText(p.getName());
            binding.tvPrice.setText(p.getPrice().toString());
            binding.clLayout.setOnClickListener(v -> onClickProduct.onClickProduct(p));
        }
    }

    interface OnClickProduct {
        void onClickProduct(Product p);
    }
}
