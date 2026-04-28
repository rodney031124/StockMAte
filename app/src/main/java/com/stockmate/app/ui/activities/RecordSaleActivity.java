package com.stockmate.app.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Sale;
import com.stockmate.app.data.repository.ProductRepository;
import com.stockmate.app.data.repository.SaleRepository;
import com.stockmate.app.utils.CurrencyUtils;

public class RecordSaleActivity extends AppCompatActivity {
    private TextView tvProductName, tvUnitPrice;
    private EditText etQuantity;
    private Button btnRecordSale;
    private String productId, productName;
    private double productPrice;
    private SaleRepository saleRepository;
    private ProductRepository productRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sale);
        
        productId = getIntent().getStringExtra("product_id");
        productName = getIntent().getStringExtra("product_name");
        productPrice = getIntent().getDoubleExtra("product_price", 0);
        
        saleRepository = new SaleRepository();
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
        tvProductName = findViewById(R.id.tv_product_name);
        tvUnitPrice = findViewById(R.id.tv_unit_price);
        etQuantity = findViewById(R.id.et_quantity);
        btnRecordSale = findViewById(R.id.btn_record_sale);
        
        tvProductName.setText(productName);
        tvUnitPrice.setText(CurrencyUtils.format(productPrice));
    }
    
    private void setupClickListeners() {
        btnRecordSale.setOnClickListener(v -> recordSale());
    }
    
    private void recordSale() {
        String quantityStr = etQuantity.getText().toString().trim();
        if (quantityStr.isEmpty()) {
            etQuantity.setError("Enter quantity");
            etQuantity.requestFocus();
            return;
        }
        
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            etQuantity.requestFocus();
            return;
        }
        
        double profit = productPrice * 0.3; // 30% profit margin
        Sale sale = new Sale(productId, productName, quantity, productPrice, profit);
        
        saleRepository.recordSale(sale, new SaleRepository.SaleCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // Update product stock
                updateProductStock(quantity);
                Toast.makeText(RecordSaleActivity.this, "Sale recorded!", Toast.LENGTH_SHORT).show();
                finish();
            }
            
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(RecordSaleActivity.this, "Failed to record sale", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void updateProductStock(int soldQuantity) {
        productRepository.getAllProducts(new ProductRepository.FirestoreCallback<java.util.List<com.stockmate.app.data.models.Product>>() {
            @Override
            public void onSuccess(java.util.List<com.stockmate.app.data.models.Product> products) {
                for (com.stockmate.app.data.models.Product p : products) {
                    if (p.getId().equals(productId)) {
                        int newQuantity = p.getQuantity() - soldQuantity;
                        productRepository.updateProductQuantity(productId, newQuantity, null);
                        break;
                    }
                }
            }
            
            @Override
            public void onFailure(Exception e) {
                // Handle error
            }
        });
    }
}