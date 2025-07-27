package com.example.maystech.ui.order;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maystech.R;
import com.example.maystech.data.model.Delivery;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.TotalCart;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.ActivityOrderBinding;
import com.example.maystech.utils.SharedPrefManager;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    SharedPrefManager pref;

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
        pref = SharedPrefManager.getInstance(this);
        User u = pref.getUserInfo();

        displayAddress(u);

        Intent intent = getIntent();
        TotalCart totalCart = (TotalCart) intent.getSerializableExtra("total");
        ArrayList<ItemProductInCart> products = (ArrayList<ItemProductInCart>) intent.getSerializableExtra("products");

    }
    void displayAddress(User u)
    {
        binding.tvUsername.setText(u.getUsername());
        binding.tvPhoneNumber.setText(u.getPhoneNumber());
        binding.tvAddress.setText(String.format("%s, %s, %s, %s", u.getProvince(), u.getDistrict(), u.getWard(), u.getAddressDetails()));
    }

    void displayDeliveryInfo(Delivery delivery)
    {
        binding.tvTotalPayment.setText(String.valueOf(delivery.getTotalPrice()));
    }

}