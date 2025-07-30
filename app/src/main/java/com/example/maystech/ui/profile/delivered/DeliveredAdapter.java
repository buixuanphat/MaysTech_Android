package com.example.maystech.ui.profile.delivered;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.databinding.ItemProductDeliveredBinding;
import com.example.maystech.ui.feedback.FeedbackActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.DeliveredViewHolder> {

    private List<Delivery> list;
    private Context context;
    DeliveryDetailsRepository deliveryDetailsRepository;

    public DeliveredAdapter(Context context) {
        this.list = new ArrayList<>();
        deliveryDetailsRepository = new DeliveryDetailsRepository();
        this.context = context;
    }

    public void setData(List<Delivery> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DeliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductDeliveredBinding binding = ItemProductDeliveredBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DeliveredViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredViewHolder holder, int position) {
        Delivery delivery = list.get(position);
        holder.binding.tvTotalOrder.setText(String.valueOf(delivery.getTotalPrice()));
        holder.binding.tvAmountTotal.setText(String.valueOf(delivery.getTotalAmount()));
        if (!delivery.getHasFeedback()) {
            holder.binding.btnFeedback.setVisibility(VISIBLE);

            holder.binding.btnFeedback.setOnClickListener(v ->
            {
                Intent intent = new Intent(context, FeedbackActivity.class);
                intent.putExtra("deliveryId", delivery.getId());
                context.startActivity(intent);
            });
        }
        else
        {
            holder.binding.btnFeedback.setVisibility(GONE);
        }

        MutableLiveData<ItemProductOrder> product = new MutableLiveData<>();
        product.observe((LifecycleOwner) context, i ->
        {
            Glide.with(context).load(i.getImage()).into(holder.binding.ivImage);
            holder.binding.tvName.setText(i.getName());
            holder.binding.tvAmountTotal.setText( "Số lượng: " +String.valueOf(i.getTotalAmount()));
            holder.binding.tvPriceTotal.setText("Thành tiền: " +String.valueOf(i.getTotalPrice()));
        });

        deliveryDetailsRepository.getProductInDelivery(delivery.getId(), new Callback<ApiResponse<List<ItemProductOrder>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductOrder>>> call, Response<ApiResponse<List<ItemProductOrder>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    product.setValue(response.body().getData().get(0));
                }
                else
                {
                    Log.e("Fetch first product of delivery error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductOrder>>> call, Throwable t) {
                Log.e("Fetch first product of delivery failure", t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DeliveredViewHolder extends RecyclerView.ViewHolder {

        ItemProductDeliveredBinding binding;

        public DeliveredViewHolder(ItemProductDeliveredBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
