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
import com.stockmate.app.R;
import com.stockmate.app.ui.activities.AddProductActivity;
import com.stockmate.app.ui.adapters.LowStockAlertAdapter;
import com.stockmate.app.ui.viewmodels.DashboardViewModel;
import java.text.NumberFormat;
import java.util.Locale;

public class DashboardFragment extends Fragment {
    private DashboardViewModel viewModel;
    private TextView tvTotalProducts, tvTotalSales, tvLowStockCount, tvMonthlySales;
    private RecyclerView rvLowStock;
    private LowStockAlertAdapter adapter;
    private NumberFormat currencyFormat;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));
        
        initViews(view);
        setupRecyclerView();
        setupViewModel();
        setupClickListeners(view);
        
        return view;
    }

    private void initViews(View view) {
        tvTotalProducts = view.findViewById(R.id.tv_total_products);
        tvTotalSales = view.findViewById(R.id.tv_total_sales);
        tvLowStockCount = view.findViewById(R.id.tv_low_stock_count);
        tvMonthlySales = view.findViewById(R.id.tv_monthly_sales);
        rvLowStock = view.findViewById(R.id.rv_low_stock_alerts);
    }

    private void setupRecyclerView() {
        adapter = new LowStockAlertAdapter();
        rvLowStock.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLowStock.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new DashboardViewModel();
        
        viewModel.getTotalProducts().observe(getViewLifecycleOwner(), total -> 
            tvTotalProducts.setText(String.valueOf(total))
        );
        
        viewModel.getTotalSales().observe(getViewLifecycleOwner(), total -> {
            tvTotalSales.setText(currencyFormat.format(total));
            // For now, let's use a variation or same value for monthly if data is mock
            if (tvMonthlySales != null) {
                tvMonthlySales.setText(currencyFormat.format(total * 1.5)); 
            }
        });
        
        viewModel.getLowStockProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                adapter.setProducts(products);
                if (tvLowStockCount != null) {
                    tvLowStockCount.setText(String.valueOf(products.size()));
                }
            }
        });
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.btn_add_product_quick).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddProductActivity.class));
        });
    }
}
