package com.example.maystech.ui.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maystech.R;
import com.example.maystech.data.model.Category;
import com.example.maystech.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    Context context;
    OnClickCategory onClickCategory;
    int current = -1;
    public CategoryAdapter(Context context, OnClickCategory onClickCategory) {
        this.context = context;
        this.categoryList = new ArrayList<>();
        this.onClickCategory =  onClickCategory;
    }

    public void setData(List<Category> categories) {
        this.categoryList = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.binding.name.setText(category.getName());
        if(position==current)
        {
            holder.binding.name.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.background.setBackgroundResource(R.drawable.shape_layout_selected);
        }
        else
        {
            holder.binding.name.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.binding.background.setBackgroundResource(R.drawable.shape_layout_no_selected);
        }
        holder.binding.background.setOnClickListener(v->
        {
            current = holder.getAdapterPosition();
            notifyDataSetChanged();

            onClickCategory.onClick(category.getId());
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface OnClickCategory{
        void onClick(int categoryId);
    }
}
