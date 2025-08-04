package com.example.maystech.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maystech.R;
import com.example.maystech.data.api.ApiClient;
import com.example.maystech.data.api.ApiResponse;
import com.example.maystech.data.api.ApiService;
import com.example.maystech.data.api.GhnApiClient;
import com.example.maystech.data.api.GhnApiResponse;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.ItemProductOrder;
import com.example.maystech.data.model.ShippingFeeRequest;
import com.example.maystech.data.model.ShippingFeeResponse;
import com.example.maystech.data.model.ShippingServiceRequest;
import com.example.maystech.data.model.ShippingServiceResponse;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.GhnRepository;
import com.example.maystech.databinding.ActivityOrderBinding;
import com.example.maystech.utils.STATIC;
import com.example.maystech.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    SharedPrefManager pref;
    User u;
    Delivery delivery;
    List<ItemProductOrder> productOrdersList;
    OrderActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);

        productOrdersList = new ArrayList<>();

        pref = SharedPrefManager.getInstance(this);
        u = pref.getUserInfo();
        String token = "Bearer "+pref.getToken();

        displayAddress(u);

        Intent intent = getIntent();
        delivery = (Delivery) intent.getSerializableExtra("delivery");
        displayDeliveryInfo(delivery);

        ArrayList<ItemProductInCart> productsInCartList = (ArrayList<ItemProductInCart>) intent.getSerializableExtra("products");
        for(int i =0; i<productsInCartList.size(); i++)
        {
            productOrdersList.add(convertProductInCartToOrder(productsInCartList.get(i)));
        }

        ProductInOrderListAdapter adapter = new ProductInOrderListAdapter(this);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProducts.setAdapter(adapter);
        adapter.setData(productOrdersList);

        // === ĐẶT HÀNG ===
        binding.btnOrder.setOnClickListener(v ->
        {
            viewModel.postDelivery(u.getId(), token, delivery);
        });

        viewModel.getDelivery().observe(this, d ->
        {
            productOrdersList.forEach(p -> p.setDeliveryId(d.getId()));
            viewModel.postProductToDelivery(d.getId(), productOrdersList);

        });

        viewModel.getMessage().observe(this, m ->
        {
            Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
            if (m.equals("Đặt hàng thành công")) finish();
        });

    }
    void displayAddress(User u)
    {
        binding.tvUsername.setText(u.getUsername());
        binding.tvPhoneNumber.setText(u.getPhoneNumber());
        binding.tvAddress.setText(String.format("%s, %s, %s, %s", u.getProvince(), u.getDistrict(), u.getWard(), u.getAddressDetails()));
    }

    void displayDeliveryInfo(Delivery delivery)
    {
        DecimalFormat formatter = new DecimalFormat("#,###");
        binding.tvTotalPrice.setText(String.valueOf(formatter.format(delivery.getTotalPrice())));
    }

    ItemProductOrder convertProductInCartToOrder(ItemProductInCart itemProductInCart)
    {
        ItemProductOrder itemProductOrder = new ItemProductOrder();
        itemProductOrder.setProductId(itemProductInCart.getProductId());
        itemProductOrder.setName(itemProductInCart.getProductName());
        itemProductOrder.setImage(itemProductInCart.getProductImage());
        itemProductOrder.setTotalAmount(itemProductInCart.getAmount());
        itemProductOrder.setTotalPrice(itemProductInCart.getTotalPrice());
        return itemProductOrder;
    }

}