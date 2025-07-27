package com.example.maystech.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.databinding.ItemProductInOrderListBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ProductOrderViewHolder> {

    private List<Delivery> deliveryList;
    private ItemProductInCart item;
    private Context context;
    private DeliveryDetailsRepository deliveryDetailsRepository;

    public ProductOrderAdapter(Context context) {
        this.deliveryList = new ArrayList<>();
        this.context = context;
        this.deliveryDetailsRepository = new DeliveryDetailsRepository();
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
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.binding.tvTotalOrder.setText(String.format("Tổng (%d sản phẩm)\n %s", delivery.getTotalAmount(), formatter.format(delivery.getTotalPrice())));

        MutableLiveData<ItemProductOrder> item = new MutableLiveData<>();
        item.observe((LifecycleOwner) context, i ->
        {
            Glide.with(context).load(i.getImage()).into(holder.binding.ivImage);
            holder.binding.tvName.setText(i.getName());
            holder.binding.tvPrice.setText(String.valueOf(i.getTotalPrice()));
            holder.binding.tvAmountTotal.setText( "Số lượng: " +String.valueOf(i.getTotalAmount()));
            holder.binding.tvPriceTotal.setText("Thành tiền: " +String.valueOf(i.getTotalPrice()));
        });

        deliveryDetailsRepository.getProductInDelivery(delivery.getId(), new Callback<ApiResponse<List<ItemProductOrder>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductOrder>>> call, Response<ApiResponse<List<ItemProductOrder>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    item.setValue(response.body().getData().get(0));
                } else {
                    Log.e("Fetch product of delivery error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductOrder>>> call, Throwable t) {
                Log.e("Fetch product in delivery failure", t.getMessage());
            }
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

    public void setData(ItemProductInCart item) {
        this.item = item;
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
