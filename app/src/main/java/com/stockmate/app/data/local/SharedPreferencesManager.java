package com.stockmate.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.stockmate.app.StockMateApplication;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "StockMatePrefs";
    private static final String KEY_USER_LOGGED_IN = "user_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";
    
    private static SharedPreferencesManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPreferencesManager() {
        sharedPreferences = StockMateApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
        }
        return instance;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(KEY_USER_LOGGED_IN, loggedIn).apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_USER_LOGGED_IN, false);
    }

    public void setUserEmail(String email) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
