package com.stockmate.app.data.repository;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockmate.app.data.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final DatabaseReference productsRef;
    
    public ProductRepository() {
        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        productsRef = database.getReference("products");
    }
    
    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    public interface RealtimeUpdateCallback {
        void onDataChanged(List<Product> products);
        void onError(Exception e);
    }
    
    // Listen for real-time updates for all products
    public void observeAllProducts(RealtimeUpdateCallback callback) {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if (product != null) {
                        product.setId(postSnapshot.getKey());
                        products.add(product);
                    }
                }
                callback.onDataChanged(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }
    
    public void addProduct(Product product, FirestoreCallback<String> callback) {
        String productId = productsRef.push().getKey();
        if (productId != null) {
            product.setId(productId);
            productsRef.child(productId).setValue(product)
                .addOnSuccessListener(aVoid -> callback.onSuccess(productId))
                .addOnFailureListener(callback::onFailure);
        }
    }

    public void updateProduct(Product product, FirestoreCallback<Void> callback) {
        if (product.getId() != null) {
            productsRef.child(product.getId()).setValue(product)
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e);
                });
        }
    }

    public void deleteProduct(String productId, FirestoreCallback<Void> callback) {
        productsRef.child(productId).removeValue()
            .addOnSuccessListener(aVoid -> callback.onSuccess(null))
            .addOnFailureListener(callback::onFailure);
    }

    public void updateProductQuantity(String productId, int newQuantity, FirestoreCallback<Void> callback) {
        productsRef.child(productId).child("quantity").setValue(newQuantity)
            .addOnSuccessListener(aVoid -> {
                if (callback != null) callback.onSuccess(null);
            })
            .addOnFailureListener(e -> {
                if (callback != null) callback.onFailure(e);
            });
    }

    // Single-fetch method for legacy support or specific needs
    public void getAllProducts(FirestoreCallback<List<Product>> callback) {
        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if (product != null) {
                        product.setId(postSnapshot.getKey());
                        products.add(product);
                    }
                }
                callback.onSuccess(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }

    public void getLowStockProducts(int threshold, FirestoreCallback<List<Product>> callback) {
        productsRef.orderByChild("quantity").endAt(threshold).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if (product != null) {
                        product.setId(postSnapshot.getKey());
                        products.add(product);
                    }
                }
                callback.onSuccess(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }
}