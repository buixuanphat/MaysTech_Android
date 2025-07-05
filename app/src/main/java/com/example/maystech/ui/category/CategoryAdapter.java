package com.example.maystech.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maystech.data.model.Category;
import com.example.maystech.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> cats;
    private Context context;
    private OnItemClickListener listener;

    public CategoryAdapter(List<Category> cats, Context context, OnItemClickListener listener) {
        this.cats = cats;
        this.context = context;
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

        void bind(Category cat)
        {
            binding.name.setText(cat.getName());
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(cat));
        }
    }


}
