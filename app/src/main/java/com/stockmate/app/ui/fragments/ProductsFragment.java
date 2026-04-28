package com.stockmate.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stockmate.app.R;
import com.stockmate.app.ui.activities.AddProductActivity;
import com.stockmate.app.ui.adapters.ProductAdapter;
import com.stockmate.app.ui.viewmodels.ProductViewModel;

public class ProductsFragment extends Fragment {
    private ProductViewModel viewModel;
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private FloatingActionButton fabAddProduct;
    private TextView tvItemCount;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        initViews(view);
        setupRecyclerView();
        setupClickListeners(view);
        setupViewModel();
        return view;
    }
    
    private void initViews(View view) {
        rvProducts = view.findViewById(R.id.rv_products);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        tvItemCount = view.findViewById(R.id.tv_item_count);
    }
    
    private void setupRecyclerView() {
        productAdapter = new ProductAdapter(product -> {
            // Navigate to product details
            Intent intent = new Intent(getActivity(), com.stockmate.app.ui.activities.ProductDetailsActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(productAdapter);
    }
    
    private void setupClickListeners(View view) {
        fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddProductActivity.class);
            startActivity(intent);
        });
        
        view.findViewById(R.id.iv_search).setOnClickListener(v -> {
            // Show search
            android.widget.Toast.makeText(getContext(), "Search coming soon", android.widget.Toast.LENGTH_SHORT).show();
        });
    }
    
    private void setupViewModel() {
        viewModel = new ProductViewModel();
        
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                productAdapter.setProducts(products);
                if (tvItemCount != null) {
                    tvItemCount.setText(products.size() + " items in stock");
                }
            }
        });
        
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Handle loading state
        });
    }
}