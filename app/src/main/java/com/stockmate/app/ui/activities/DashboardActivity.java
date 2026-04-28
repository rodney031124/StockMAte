package com.stockmate.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.stockmate.app.ui.fragments.SettingsFragment;

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
        getSupportActionBar().setTitle("StockMate");
    }
    
    private void setupNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_dashboard) {
                loadFragment(new DashboardFragment());
                getSupportActionBar().setTitle("Dashboard");
                return true;
            } else if (itemId == R.id.nav_products) {
                loadFragment(new ProductsFragment());
                getSupportActionBar().setTitle("Products");
                return true;
            } else if (itemId == R.id.nav_analytics) {
                loadFragment(new AnalyticsFragment());
                getSupportActionBar().setTitle("Analytics");
                return true;
            } else if (itemId == R.id.nav_settings) {
                loadFragment(new SettingsFragment());
                getSupportActionBar().setTitle("Settings");
                return true;
            }
            return false;
        });
    }
    
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, fragment)
            .commit();
    }
    
    private void logout() {
        userRepository.logout();
        SharedPreferencesManager.getInstance().clear();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
