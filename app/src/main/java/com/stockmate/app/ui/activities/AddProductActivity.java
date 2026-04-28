package com.stockmate.app.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.data.repository.ProductRepository;
import com.stockmate.app.utils.CurrencyUtils;
import com.stockmate.app.utils.ValidationUtils;

public class AddProductActivity extends AppCompatActivity {
    private TextInputEditText etProductName, etCategory, etBuyingPrice, etSellingPrice, etQuantity;
    private Button btnAddProduct;
    private ProductRepository productRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        
        productRepository = new ProductRepository();
        initViews();
        setupClickListeners();
        
        // Set up toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    
    private void initViews() {
        etProductName = findViewById(R.id.et_product_name);
        etCategory = findViewById(R.id.et_category);
        etBuyingPrice = findViewById(R.id.et_buying_price);
        etSellingPrice = findViewById(R.id.et_selling_price);
        etQuantity = findViewById(R.id.et_quantity);
        btnAddProduct = findViewById(R.id.btn_add_product);
    }
    
    private void setupClickListeners() {
        btnAddProduct.setOnClickListener(v -> addProduct());
    }
    
    private void addProduct() {
        String name = etProductName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String buyingPriceStr = etBuyingPrice.getText().toString().trim();
        String sellingPriceStr = etSellingPrice.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        
        if (name.isEmpty()) {
            etProductName.setError("Product name required");
            etProductName.requestFocus();
            return;
        }
        
        if (category.isEmpty()) {
            etCategory.setError("Category required");
            etCategory.requestFocus();
            return;
        }
        
        double buyingPrice = CurrencyUtils.parse(buyingPriceStr);
        double sellingPrice = CurrencyUtils.parse(sellingPriceStr);
        int quantity = Integer.parseInt(quantityStr.isEmpty() ? "0" : quantityStr);
        
        Product product = new Product(name, category, sellingPrice, quantity, "");
        product.setBuyingPrice(buyingPrice);
        
        productRepository.addProduct(product, new ProductRepository.FirestoreCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(AddProductActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
            
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddProductActivity.this, "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}