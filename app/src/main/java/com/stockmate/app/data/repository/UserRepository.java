package com.stockmate.app.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.google.firebase.auth.FirebaseUser;
import com.stockmate.app.data.models.User;
import java.util.UUID;

public class UserRepository {
    private static boolean isLoggedIn = false;
    private static String currentUser = null;
    
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }
    
    public interface UserRepositoryCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
    
    public void login(String email, String password, AuthCallback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Mock login - accept any email with password length >= 6
            if (password != null && password.length() >= 6) {
                isLoggedIn = true;
                currentUser = email;
                callback.onSuccess(null);
            } else {
                callback.onFailure(new Exception("Invalid credentials"));
            }
        }, 500);
    }
    
    public void register(String email, String password, String displayName, AuthCallback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Mock registration - always succeeds
            if (email != null && !email.isEmpty() && password != null && password.length() >= 6) {
                isLoggedIn = true;
                currentUser = email;
                callback.onSuccess(null);
            } else {
                callback.onFailure(new Exception("Registration failed. Please check your details."));
            }
        }, 500);
    }
    
    public void logout() {
        isLoggedIn = false;
        currentUser = null;
    }
    
    public FirebaseUser getCurrentUser() {
        return null;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public void getUserData(UserRepositoryCallback<User> callback) {
        User mockUser = new User(currentUser != null ? currentUser : "demo@stockmate.co.za", "Demo User");
        mockUser.setId(UUID.randomUUID().toString());
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(mockUser);
        }, 300);
    }
}