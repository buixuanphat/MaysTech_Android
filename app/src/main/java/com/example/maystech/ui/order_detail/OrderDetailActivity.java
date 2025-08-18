package com.example.maystech.ui.order_detail;

import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maystech.R;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.repository.DeliveryDetailsRepository;
import com.example.maystech.data.repository.DeliveryRepository;
import com.example.maystech.databinding.ActivityOrderDetailBinding;
import com.example.maystech.utils.STATIC;
import com.google.android.material.internal.ManufacturerUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private ActivityOrderDetailBinding binding;
    private MutableLiveData<List<ItemProductOrder>> productList;
    private MutableLiveData<Delivery> delivery;
    private MutableLiveData<String> message;
    private OrderDetailAdapter orderDetailAdapter;
    private DeliveryDetailsRepository deliveryDetailsRepository;
    private DeliveryRepository deliveryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productList = new MutableLiveData<>();
        delivery = new MutableLiveData<>();
        message = new MutableLiveData<>();
        orderDetailAdapter = new OrderDetailAdapter(this);
        deliveryDetailsRepository = new DeliveryDetailsRepository();
        deliveryRepository = new DeliveryRepository();

        Intent intent = getIntent();
        int id = intent.getIntExtra("deliveryId", -1);

        // === HIỂN THỊ LIST SẢN PHẨM ===

        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProducts.setAdapter(orderDetailAdapter);
        productList.observe(this, l ->
        {
            orderDetailAdapter.setData(l);
        });

        fetchProductInDelivery(id);


        // === HỂN THỊ THÔNG TIN ĐƠN HÀNG ===
        delivery.observe(this, d ->
        {
            binding.tvUsername.setText(d.getUsername());
            binding.tvPhoneNumber.setText(d.getPhoneNumber());
            binding.tvAddress.setText(d.getAddress());
            binding.tvTotalPayment.setText(STATIC.formatPrice(d.getTotalPrice()));
            if(d.getStatus().equals(STATIC.DELIVERED) || d.getStatus().equals(STATIC.CANCELLED) )
            {
                binding.btnCancel.setVisibility(GONE);
            }
            if(d.getCancellationRequest())
            {
                binding.btnCancel.setClickable(false);
                binding.btnCancel.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray));
            }
        });
        fetchDelivery(id);


        // === GỬI YÊU CẦU HỦY ===
        binding.btnCancel.setOnClickListener(v ->
        {
            requestCancel(id);
        });
        message.observe(this, m->
        {
            Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
            fetchDelivery(id);
        });


    }
    public void fetchProductInDelivery(int deliveryId)
    {
        deliveryDetailsRepository.getProductInDelivery(deliveryId, new Callback<ApiResponse<List<ItemProductOrder>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ItemProductOrder>>> call, Response<ApiResponse<List<ItemProductOrder>>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    productList.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch Product in delivery error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ItemProductOrder>>> call, Throwable t) {
                Log.e("Fetch Product in delivery failure", t.getMessage());
            }
        });
    }

    public void fetchDelivery(int id)
    {
        deliveryRepository.getDeliveryById(id, new Callback<ApiResponse<Delivery>>() {
            @Override
            public void onResponse(Call<ApiResponse<Delivery>> call, Response<ApiResponse<Delivery>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    delivery.setValue(response.body().getData());
                }
                else
                {
                    Log.e("Fetch delivery error", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Delivery>> call, Throwable t) {
                Log.e("Fetch delivery failure", t.getMessage());
            }
        });
    }

    public void requestCancel(int id)
    {
        deliveryRepository.requestCancel(id, new Callback<ApiResponse<Delivery>>() {
            @Override
            public void onResponse(Call<ApiResponse<Delivery>> call, Response<ApiResponse<Delivery>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    message.setValue("Đã gửi yêu cầu");
                }
                else
                {
                    Log.e("Request cancel error", response.code()+"");
                    message.setValue("Lỗi! Không thể gửi yêu cầu");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Delivery>> call, Throwable t) {
                Log.e("Request cancel failure", t.getMessage());
                message.setValue("Lỗi! Không thể gửi yêu cầu");
            }
        });
    }


}