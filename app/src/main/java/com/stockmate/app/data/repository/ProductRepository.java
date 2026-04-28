package com.stockmate.app.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.stockmate.app.data.models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRepository {
    private static List<Product> mockProducts = new ArrayList<>();
    
    static {
        mockProducts.add(createProduct("Coca Cola", "Beverages", 10.00, 15.00, 5));
        mockProducts.add(createProduct("Chips - Simba", "Snacks", 5.00, 10.00, 3));
        mockProducts.add(createProduct("White Bread", "Bakery", 10.00, 15.00, 12));
        mockProducts.add(createProduct("Milk 1L", "Dairy", 15.00, 22.00, 8));
        mockProducts.add(createProduct("Coca Cola 500ml", "Beverages", 8.50, 12.00, 24));
        mockProducts.add(createProduct("Airtime R5", "Vouchers", 5.00, 5.00, 50));
    }
    
    private static Product createProduct(String name, String category, double buyingPrice, double sellingPrice, int quantity) {
        Product product = new Product(name, category, sellingPrice, quantity, "");
        product.setId(UUID.randomUUID().toString());
        product.setBuyingPrice(buyingPrice);
        return product;
    }
    
    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
    
    public void getAllProducts(FirestoreCallback<List<Product>> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(new ArrayList<>(mockProducts));
        }, 500);
    }
    
    public void getLowStockProducts(int threshold, FirestoreCallback<List<Product>> callback) {
        List<Product> lowStock = new ArrayList<>();
        for (Product p : mockProducts) {
            if (p.getQuantity() < threshold) {
                lowStock.add(p);
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(lowStock);
        }, 300);
    }

    public void addProduct(Product product, FirestoreCallback<String> callback) {
        product.setId(UUID.randomUUID().toString());
        mockProducts.add(product);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(product.getId());
        }, 300);
    }

    public void updateProduct(Product product, FirestoreCallback<Void> callback) {
        for (int i = 0; i < mockProducts.size(); i++) {
            if (mockProducts.get(i).getId().equals(product.getId())) {
                mockProducts.set(i, product);
                break;
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (callback != null) callback.onSuccess(null);
        }, 300);
    }

    public void deleteProduct(String productId, FirestoreCallback<Void> callback) {
        mockProducts.removeIf(p -> p.getId().equals(productId));
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(null);
        }, 300);
    }

    public void updateProductQuantity(String productId, int newQuantity, FirestoreCallback<Void> callback) {
        for (Product p : mockProducts) {
            if (p.getId().equals(productId)) {
                p.setQuantity(newQuantity);
                break;
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (callback != null) callback.onSuccess(null);
        }, 300);
    }
}
