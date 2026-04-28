package com.stockmate.app;

import android.app.Application;

public class StockMateApplication extends Application {
    private static StockMateApplication instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    
    public static StockMateApplication getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Application not yet created");
        }
        return instance;
    }
}
