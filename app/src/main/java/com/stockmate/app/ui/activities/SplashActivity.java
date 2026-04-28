package com.stockmate.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.stockmate.app.R;
import com.stockmate.app.data.local.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(() -> {
            if (SharedPreferencesManager.getInstance().isUserLoggedIn()) {
                startActivity(new Intent(this, DashboardActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}
