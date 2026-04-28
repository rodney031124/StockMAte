package com.stockmate.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.stockmate.app.R;
import com.stockmate.app.databinding.ActivityLoginBinding;
import com.stockmate.app.data.local.SharedPreferencesManager;
import com.stockmate.app.data.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserRepository userRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        userRepository = new UserRepository();
        setupClickListeners();
    }
    
    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> attemptLogin());
        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        binding.tvForgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }
    
    private void showForgotPasswordDialog() {
        EditText resetEmail = new EditText(this);
        resetEmail.setHint("Enter your email");
        resetEmail.setPadding(60, 40, 60, 40);

        new AlertDialog.Builder(this)
            .setTitle("Reset Password")
            .setMessage("We will send a reset link to your email address.")
            .setView(resetEmail)
            .setPositiveButton("Send", (dialog, which) -> {
                String email = resetEmail.getText().toString().trim();
                if (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    sendPasswordResetEmail(email);
                } else {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void sendPasswordResetEmail(String email) {
        setLoading(true);
        // Using Firebase Auth through UserRepository or directly for simplicity if repo doesn't have it
        com.google.firebase.auth.FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener(task -> {
                setLoading(false);
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Reset email sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    
    private void attemptLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        
        if (!validateInputs(email, password)) {
            return;
        }
        
        setLoading(true);
        
        userRepository.login(email, password, new UserRepository.AuthCallback() {
            @Override
            public void onSuccess(com.google.firebase.auth.FirebaseUser user) {
                setLoading(false);
                SharedPreferencesManager.getInstance().setUserLoggedIn(true);
                SharedPreferencesManager.getInstance().setUserEmail(email);
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }
            
            @Override
            public void onFailure(Exception e) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private boolean validateInputs(String email, String password) {
        boolean isValid = true;
        
        if (email.isEmpty()) {
            binding.etEmail.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Please enter a valid email");
            isValid = false;
        }
        
        if (password.isEmpty()) {
            binding.etPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }
        
        return isValid;
    }
    
    private void setLoading(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnLogin.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        binding.etEmail.setEnabled(!isLoading);
        binding.etPassword.setEnabled(!isLoading);
        binding.tvRegister.setEnabled(!isLoading);
        binding.tvForgotPassword.setEnabled(!isLoading);
    }
}