package com.example.maystech.ui.category;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maystech.data.model.Brand;
import com.example.maystech.data.model.Category;
import com.example.maystech.data.model.Product;
import com.example.maystech.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter adapter;
    private ProductAdapter productAdapter;
    private int currentCat;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get categories
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        adapter = new CategoryAdapter( new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category cat) {
                adapter.setCurrentItemId(cat.getId());

                //update clicked category color
                adapter.notifyDataSetChanged();

                // fetch product of clicked category
                viewModel.fetchProductOfCategory(cat.getId());

                // fetch brand of clicked category
                viewModel.fetchBrandOfCategory(cat.getId());

                currentCat = cat.getId();
            }
        });

        binding.rvCategory.setAdapter(adapter);
        binding.rvCategory.setLayoutManager(linearLayoutManager);

        viewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setData(categories);
            }
        });

        viewModel.fetchCategories(new OnDataLoaded() {
            @Override
            public void onDataLoaded(int firstItem) {
                // choose the first category
                adapter.setCurrentItemId(firstItem);
                viewModel.fetchProductOfCategory(firstItem);
                viewModel.fetchBrandOfCategory(firstItem);
                currentCat = firstItem;

            }
        });


        //Get products
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        productAdapter = new ProductAdapter(new ProductAdapter.OnClickProduct() {
            @Override
            public void onClickProduct(Product p) {

            }
        });
        binding.rvProduct.setAdapter(productAdapter);
        binding.rvProduct.setLayoutManager(gridLayoutManager);
        viewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setData(products);
            }
        });




        //Drawer layout
        GridLayoutManager gridLayoutManagerBrand = new GridLayoutManager(requireContext(), 2);
        DrawerLayout drawerLayout = binding.drawer;
        binding.ivFilter.setOnClickListener(v ->
        {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        BrandAdapter brandAdapter = new BrandAdapter(new BrandAdapter.OnBrandClick() {
            @Override
            public void onBrandClick(Brand brand) {
                viewModel.fetProductOfCategoryAndBrand(currentCat, brand.getId());
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        binding.rvBrand.setAdapter(brandAdapter);
        binding.rvBrand.setLayoutManager(gridLayoutManagerBrand);
        viewModel.getBrandOfCategory().observe(getViewLifecycleOwner(), new Observer<List<Brand>>() {
            @Override
            public void onChanged(List<Brand> brands) {
                brandAdapter.setData(brands);
            }
        });

    }


}
