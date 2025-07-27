package com.example.maystech.ui.cart;

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

import com.example.maystech.utils.SharedPrefManager;
import com.example.maystech.data.model.ItemProductInCart;
import com.example.maystech.data.model.TotalCart;
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
    private TotalCart totalCart;
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
        int id = user.getId();

        RecyclerView rvProduct = binding.rvProducts;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false);
        rvProduct.setLayoutManager(linearLayoutManager);
        productCartAdapter = new ProductCartAdapter(new ProductCartAdapter.OnClick() {
            @Override
            public void onAdd(ItemProductInCart itemProductInCart) {
                viewModel.addProductToCart(token, id, itemProductInCart.getProdId());
            }

            @Override
            public void onRemove(ItemProductInCart itemProductInCart) {
                viewModel.deleteProductFromCart(token, itemProductInCart.getId());
            }

            @Override
            public void onClick(ItemProductInCart itemProductInCart) {
                Intent intent = new Intent(requireContext(), ProductDetailsActivity.class);
                intent.putExtra("prodId", itemProductInCart.getProdId());
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

        viewModel.getProductInCart(token,id);
        viewModel.getTotalCart(token, id);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productCartAdapter.setData(products);
            itemProductInCarts.clear();
            itemProductInCarts.addAll(products);
        });


        viewModel.getAddToCartMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        viewModel.getTotal().observe(getViewLifecycleOwner(), total -> {
            binding.tvPriceTotalChosen.setText("Tổng: " + String.valueOf(total.getTotalPrice()));
            binding.tvAmountTotalChosen.setText("Đã chọn: "+ String.valueOf(total.getTotalAmount()));
            totalCart = total;
        });

        binding.btnOrder.setOnClickListener( v -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            intent.putExtra("total", totalCart);
            intent.putExtra("products", (Serializable) itemProductInCarts);
            startActivity(intent);
        });

        return binding.getRoot();
    }

}