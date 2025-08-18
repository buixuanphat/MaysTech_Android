package com.example.maystech.ui.category;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.maystech.R;
import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.databinding.FragmentCategoryBinding;
import com.example.maystech.ui.home.HomeViewModel;
import com.example.maystech.ui.product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    CategoryViewModel viewModel;
    int currentCat = -1;
    List<Product> productList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        productList = new ArrayList<>();

        // === GET CATEGORY ===
        CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(), new CategoryAdapter.OnClickCategory() {
            @Override
            public void onClick(int categoryId) {
                viewModel.fetchProducts(categoryId, null);
                viewModel.fetchBrandOfCategory(categoryId);
                currentCat = categoryId;
            }
        });
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategory.setAdapter(categoryAdapter);
        viewModel.getCategories().observe(getViewLifecycleOwner(), list ->
        {
            categoryAdapter.setData(list);
            binding.rvCategory.post(() -> {
                RecyclerView.ViewHolder holder = binding.rvCategory.findViewHolderForAdapterPosition(0);
                if (holder != null) {
                    holder.itemView.performClick();
                }
            });

            binding.tvDesc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvDesc.setBackgroundResource(R.drawable.shape_layout_no_selected);
            binding.tvAsc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvAsc.setBackgroundResource(R.drawable.shape_layout_no_selected);
        });
        viewModel.fetchCategories();



        // === GET PRODUCT ===
        ProductAdapter productAdapter = new ProductAdapter(requireContext());
        binding.rvProduct.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false));
        binding.rvProduct.setAdapter(productAdapter);
        viewModel.getProductList().observe(getViewLifecycleOwner(), list ->
        {
            productAdapter.setData(list);
            productList.clear();
            productList.addAll(list);

            binding.tvDesc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvDesc.setBackgroundResource(R.drawable.shape_layout_no_selected);
            binding.tvAsc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvAsc.setBackgroundResource(R.drawable.shape_layout_no_selected);

            if(list.size()==0)
            {
                binding.tvMessage.setVisibility(VISIBLE);
                binding.rvProduct.setVisibility(GONE);
            }
            else
            {
                binding.tvMessage.setVisibility(GONE);
                binding.rvProduct.setVisibility(VISIBLE);
            }
        });
        viewModel.fetchProducts(null, null);




        // === DRAWER LAYOUT ===
        DrawerLayout drawerLayout = binding.drawer;
        binding.ivFilter.setOnClickListener(v ->
        {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });


        BrandAdapter brandAdapter = new BrandAdapter(requireContext(),new BrandAdapter.OnBrandClick() {
            @Override
            public void onBrandClick(int brandId) {
                viewModel.fetchProducts(currentCat, brandId);
            }
        });
        binding.rvBrand.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false));
        binding.rvBrand.setAdapter(brandAdapter);
        viewModel.getBrandOfCategory().observe(getViewLifecycleOwner(), list ->
        {
            brandAdapter.setData(list);
        });



        binding.tvAsc.setOnClickListener(v ->
        {
            productList.sort(Comparator.comparing(Product::getPrice));
            productAdapter.setData(productList);

            binding.tvAsc.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            binding.tvAsc.setBackgroundResource(R.drawable.shape_layout_selected);

            binding.tvDesc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvDesc.setBackgroundResource(R.drawable.shape_layout_no_selected);

        });

        binding.tvDesc.setOnClickListener(v ->
        {
            productList.sort(Comparator.comparing(Product::getPrice).reversed());
            productAdapter.setData(productList);

            binding.tvAsc.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            binding.tvAsc.setBackgroundResource(R.drawable.shape_layout_no_selected);

            binding.tvDesc.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            binding.tvDesc.setBackgroundResource(R.drawable.shape_layout_selected);

        });

        binding.edtSearch.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT) {

                String kw = binding.edtSearch.getText().toString();
                viewModel.searchProductByName(kw);


                // Ẩn bàn phím
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(), 0);

                binding.edtSearch.setText("");

                return true;
            }
            return false;
        });



    }


}
