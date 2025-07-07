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
import androidx.recyclerview.widget.RecyclerView;

import com.example.maystech.R;
import com.example.maystech.data.model.Category;
import com.example.maystech.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> cats;
    private OnItemClickListener listener;
    private int currentItemId = RecyclerView.NO_POSITION;

    public CategoryAdapter(OnItemClickListener listener) {
        this.cats = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(cats.get(position));
    }

    public int getCurrentItemId() {
        return currentItemId;
    }
    public void setCurrentItemId(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(Category cat);
    }
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        void bind(Category cat)
        {
            binding.name.setText(cat.getName());
            Context context = binding.getRoot().getContext();
            if (cat.getId() == currentItemId) {
                binding.name.setTextColor(context.getColor(R.color.white));
                binding.background.setBackgroundDrawable(context.getDrawable(R.drawable.shape_layout_selected));

            } else {
                binding.name.setTextColor(context.getColor(R.color.dark_gray));
                binding.background.setBackgroundDrawable(context.getDrawable(R.drawable.shape_layout_no_selected));

            }
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(cat));
        }
    }

    public void setData(List<Category> categories) {
        this.cats = categories;
        notifyDataSetChanged();
    }


}
