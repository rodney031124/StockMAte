package com.stockmate.app.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.data.repository.ProductRepository;
import com.stockmate.app.utils.CurrencyUtils;

public class EditProductActivity extends AppCompatActivity {
    private TextInputEditText etProductName, etCategory, etBuyingPrice, etSellingPrice, etQuantity;
    private Button btnSave;
    private ProductRepository productRepository;
    private Product product;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        
        product = (Product) getIntent().getSerializableExtra("product");
        productRepository = new ProductRepository();
        initViews();
        setupClickListeners();
        
        // Set up toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        
        populateFields();
    }
    
    private void initViews() {
        etProductName = findViewById(R.id.et_product_name);
        etCategory = findViewById(R.id.et_category);
        etBuyingPrice = findViewById(R.id.et_buying_price);
        etSellingPrice = findViewById(R.id.et_selling_price);
        etQuantity = findViewById(R.id.et_quantity);
        btnSave = findViewById(R.id.btn_add_product);
        btnSave.setText("Save Changes");
    }
    
    private void populateFields() {
        etProductName.setText(product.getName());
        etCategory.setText(product.getCategory());
        etBuyingPrice.setText(String.valueOf(product.getBuyingPrice()));
        etSellingPrice.setText(String.valueOf(product.getSellingPrice()));
        etQuantity.setText(String.valueOf(product.getQuantity()));
    }
    
    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> updateProduct());
    }
    
    private void updateProduct() {
        String name = etProductName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        double buyingPrice = CurrencyUtils.parse(etBuyingPrice.getText().toString().trim());
        double sellingPrice = CurrencyUtils.parse(etSellingPrice.getText().toString().trim());
        int quantity = Integer.parseInt(etQuantity.getText().toString().trim());
        
        product.setName(name);
        product.setCategory(category);
        product.setBuyingPrice(buyingPrice);
        product.setSellingPrice(sellingPrice);
        product.setQuantity(quantity);
        
        productRepository.updateProduct(product, new ProductRepository.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(EditProductActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                finish();
            }
            
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EditProductActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}