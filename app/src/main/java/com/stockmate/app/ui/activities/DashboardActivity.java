package com.stockmate.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stockmate.app.R;
import com.stockmate.app.data.local.SharedPreferencesManager;
import com.stockmate.app.data.repository.UserRepository;
import com.stockmate.app.ui.fragments.DashboardFragment;
import com.stockmate.app.ui.fragments.ProductsFragment;
import com.stockmate.app.ui.fragments.AnalyticsFragment;

public class DashboardActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigation;
    private UserRepository userRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        userRepository = new UserRepository();
        initViews();
        setupNavigation();
        
        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment());
            bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
        }
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("StockMate");
        }
    }
    
    private void setupNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_dashboard) {
                loadFragment(new DashboardFragment());
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Dashboard");
                return true;
            } else if (itemId == R.id.nav_products) {
                loadFragment(new ProductsFragment());
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Products");
                return true;
            } else if (itemId == R.id.nav_analytics) {
                loadFragment(new AnalyticsFragment());
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Analytics");
                return true;
            } else if (itemId == R.id.nav_logout) {
                showLogoutConfirmation();
                return false; // Don't select the logout item
            }
            return false;
        });
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout", (dialog, which) -> logout())
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, fragment)
            .commit();
    }
    
    private void logout() {
        userRepository.logout();
        SharedPreferencesManager.getInstance().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}