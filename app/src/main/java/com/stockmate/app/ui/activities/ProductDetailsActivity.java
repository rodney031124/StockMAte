package com.stockmate.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.data.repository.ProductRepository;
import com.stockmate.app.utils.CurrencyUtils;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView tvProductName, tvCategory, tvQuantity, tvSellingPrice, tvSellingPriceDetail, tvBuyingPrice, tvProfit;
    private MaterialButton btnRecordSale, btnEdit, btnDelete;
    private ProductRepository productRepository;
    private Product currentProduct;
    private String productId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        
        productId = getIntent().getStringExtra("product_id");
        productRepository = new ProductRepository();
        initViews();
        setupClickListeners();
        
        // Set up toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        
        loadProductDetails();
    }
    
    private void initViews() {
        tvProductName = findViewById(R.id.tv_product_name);
        tvCategory = findViewById(R.id.tv_category);
        tvQuantity = findViewById(R.id.tv_quantity);
        tvSellingPrice = findViewById(R.id.tv_selling_price);
        tvSellingPriceDetail = findViewById(R.id.tv_selling_price_detail);
        tvBuyingPrice = findViewById(R.id.tv_buying_price);
        tvProfit = findViewById(R.id.tv_profit);
        btnRecordSale = findViewById(R.id.btn_record_sale);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
    }
    
    private void setupClickListeners() {
        btnRecordSale.setOnClickListener(v -> {
            if (currentProduct != null) {
                Intent intent = new Intent(ProductDetailsActivity.this, RecordSaleActivity.class);
                intent.putExtra("product_id", productId);
                intent.putExtra("product_name", currentProduct.getName());
                intent.putExtra("product_price", currentProduct.getSellingPrice());
                startActivity(intent);
            }
        });
        
        btnEdit.setOnClickListener(v -> {
            if (currentProduct != null) {
                Intent intent = new Intent(ProductDetailsActivity.this, EditProductActivity.class);
                intent.putExtra("product", currentProduct);
                startActivity(intent);
            }
        });
        
        btnDelete.setOnClickListener(v -> {
            if (currentProduct != null) {
                new AlertDialog.Builder(this)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete " + currentProduct.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> deleteProduct())
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });
    }
    
    private void loadProductDetails() {
        productRepository.getAllProducts(new ProductRepository.FirestoreCallback<java.util.List<Product>>() {
            @Override
            public void onSuccess(java.util.List<Product> products) {
                for (Product p : products) {
                    if (p.getId().equals(productId)) {
                        currentProduct = p;
                        displayProductDetails();
                        break;
                    }
                }
            }
            
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load product", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    
    private void displayProductDetails() {
        if (currentProduct == null) return;

        tvProductName.setText(currentProduct.getName());
        tvCategory.setText(currentProduct.getCategory());
        tvQuantity.setText(String.valueOf((int)currentProduct.getQuantity()));
        
        String sellingPriceStr = CurrencyUtils.format(currentProduct.getSellingPrice());
        tvSellingPrice.setText(sellingPriceStr);
        tvSellingPriceDetail.setText(sellingPriceStr);
        
        tvBuyingPrice.setText(CurrencyUtils.format(currentProduct.getBuyingPrice()));
        
        double profit = currentProduct.getSellingPrice() - currentProduct.getBuyingPrice();
        tvProfit.setText(CurrencyUtils.format(profit));
        
        if (currentProduct.isLowStock()) {
            tvQuantity.setTextColor(getResources().getColor(R.color.error));
        } else {
            tvQuantity.setTextColor(getResources().getColor(R.color.white));
        }
    }
    
    private void deleteProduct() {
        productRepository.deleteProduct(productId, new ProductRepository.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(ProductDetailsActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
            
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}