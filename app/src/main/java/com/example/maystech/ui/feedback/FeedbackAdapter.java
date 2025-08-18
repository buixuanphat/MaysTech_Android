package com.example.maystech.ui.feedback;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.model.Feedback;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.databinding.ItemFeedbackBinding;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    List<Feedback> producList;
    Context context;

    public FeedbackAdapter(Context context) {
        this.producList = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Feedback> producList) {
        this.producList = producList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedbackBinding binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FeedbackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = producList.get(position);
        ItemProductOrder item = producList.get(position).getProduct();
        Glide.with(context).load(item.getProductImage()).into(holder.binding.ivImage);
        holder.binding.tvName.setText(item.getProductName());
        holder.binding.ratingBar.setRating(5);
        feedback.setRating(5);

        holder.binding.editTextTextMultiLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                feedback.setComment(s.toString());
                if (s.toString().trim().length()>100)
                {
                    holder.binding.editTextTextMultiLine.setError("Vui lòng nhập nhỏ hơn 100 kí tự");
                }
                else if (s.toString().trim().isEmpty())
                {
                    holder.binding.editTextTextMultiLine.setError("Không được để trống bình luận");
                }
            }
        });

        holder.binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                feedback.setRating((int)rating);
            }
        });

    }

    @Override
    public int getItemCount() {
        return producList.size();
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder {
        ItemFeedbackBinding binding;

        public FeedbackViewHolder(ItemFeedbackBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
