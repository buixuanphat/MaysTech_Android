package com.example.maystech.ui.cart;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.maystech.data.model.Delivery;
import com.example.maystech.utils.STATIC;
import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.FragmentCartBinding;
import com.example.maystech.ui.order.OrderActivity;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    private CartViewModel viewModel;
    private ProductCartAdapter productCartAdapter;

    private List<ItemProductInCart> products;
    private Delivery delivery;
    private List<ItemProductInCart> itemProductInCarts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        viewModel = new CartViewModel();

        itemProductInCarts = new ArrayList<>();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());
        User user = sharedPrefManager.getUserInfo();
        String token = "Bearer "+ sharedPrefManager.getToken();

        RecyclerView rvProduct = binding.rvProducts;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false);
        rvProduct.setLayoutManager(linearLayoutManager);
        productCartAdapter = new ProductCartAdapter(new ProductCartAdapter.OnClick() {
            @Override
            public void onAdd(ItemProductInCart itemProductInCart) {
                viewModel.addProductToCart(token, user.getId(), itemProductInCart.getProductId());
            }

            @Override
            public void onRemove(ItemProductInCart itemProductInCart) {
                viewModel.deleteProductFromCart(token, itemProductInCart.getId());
            }

            @Override
            public void onClick(ItemProductInCart itemProductInCart) {
                Intent intent = new Intent(requireContext(), ProductDetailsActivity.class);
                intent.putExtra("prodId", itemProductInCart.getProductId());
                startActivity(intent);
            }

            @Override
            public void onCheck(CheckBox cb, ItemProductInCart itemProductInCart) {
                if(!cb.isChecked())
                {
                    viewModel.choose( token, itemProductInCart.getId(), 0);
                }
                else
                {
                    viewModel.choose(token , itemProductInCart.getId(), 1);
                }
            }
        });
        rvProduct.setAdapter(productCartAdapter);

        viewModel.getProductInCart(token,user.getId());
        viewModel.getTotalCart(token, user.getId());

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productCartAdapter.setData(products);
            itemProductInCarts.clear();
            itemProductInCarts.addAll(products);
            if(itemProductInCarts.isEmpty())
            {
                binding.tvLabel.setVisibility(VISIBLE);
            }
            else
            {
                binding.tvLabel.setVisibility(GONE);
            }
        });


        viewModel.getAddToCartMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        viewModel.getTotal().observe(getViewLifecycleOwner(), total -> {
            if(total.getTotalPrice()>0)
            {
                binding.tvAmountTotalChosen.setVisibility(VISIBLE);
                binding.tvPriceTotalChosen.setVisibility(VISIBLE);
                binding.btnOrder.setVisibility(VISIBLE);
                binding.tvPriceTotalChosen.setText("Tổng: " + STATIC.formatPrice(total.getTotalPrice()));
                binding.tvAmountTotalChosen.setText("Đã chọn: "+ String.valueOf(total.getTotalAmount()));
                delivery = total;
            }
            else
            {
                binding.tvAmountTotalChosen.setVisibility(GONE);
                binding.tvPriceTotalChosen.setVisibility(GONE);
                binding.btnOrder.setVisibility(GONE);
            }

        });

        binding.btnOrder.setOnClickListener( v -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            intent.putExtra("delivery", delivery);

            List<ItemProductInCart> productChosen = new ArrayList<>();
            for(int i =0; i< itemProductInCarts.size(); i++)
            {
                if(itemProductInCarts.get(i).isChosen())
                {
                    productChosen.add(itemProductInCarts.get(i));
                }
            }

            intent.putExtra("products", (Serializable) productChosen);
            startActivity(intent);
        });

        return binding.getRoot();
    }

}