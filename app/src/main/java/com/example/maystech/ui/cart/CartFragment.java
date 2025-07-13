package com.example.maystech.ui.cart;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.maystech.R;
import com.example.maystech.data.SharedPrefManager;
import com.example.maystech.data.model.ItemProduct;
import com.example.maystech.data.model.User;
import com.example.maystech.databinding.FragmentCartBinding;
import com.example.maystech.databinding.ItemProductCartBinding;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.util.LinkedList;
import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    private CartViewModel viewModel;
    private ProductCartAdapter productCartAdapter;

    private List<ItemProduct> products;

    private int totalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        viewModel = new CartViewModel();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(requireContext());
        User user = sharedPrefManager.getUserInfo();
        String token = "Bearer "+ sharedPrefManager.getToken();
        int id = user.getId();

        RecyclerView rvProduct = binding.rvProducts;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false);
        rvProduct.setLayoutManager(linearLayoutManager);
        productCartAdapter = new ProductCartAdapter(new ProductCartAdapter.OnClick() {
            @Override
            public void onAdd(ItemProduct itemProduct) {
                viewModel.addProductToCart(token, id, itemProduct.getProdId());
            }

            @Override
            public void onRemove(ItemProduct itemProduct) {
                viewModel.deleteProductFromCart(token, itemProduct.getId());
            }

            @Override
            public void onClick(ItemProduct itemProduct) {
                Intent intent = new Intent(requireContext(), ProductDetailsActivity.class);
                intent.putExtra("prodId", itemProduct.getProdId());
                startActivity(intent);
            }

            @Override
            public void onCheck(CheckBox cb, ItemProduct itemProduct) {
                if(!cb.isChecked())
                {
                    viewModel.choose( token,itemProduct.getId(), 0);
                }
                else
                {
                    viewModel.choose(token ,itemProduct.getId(), 1);
                }
            }
        });
        rvProduct.setAdapter(productCartAdapter);

        viewModel.getProductInCart(token,id);
        viewModel.getTotalCart(token, id);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productCartAdapter.setData(products);
        });


        viewModel.getAddToCartMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        viewModel.getTotal().observe(getViewLifecycleOwner(), total -> {
            binding.tvPriceTotalChosen.setText(String.valueOf(total.getTotalPrice()));
            binding.tvAmountTotalChosen.setText("Đã chọn "+ String.valueOf(total.getTotalAmount()));
        });

        return binding.getRoot();
    }

}