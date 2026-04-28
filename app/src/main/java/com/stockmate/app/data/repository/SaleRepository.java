package com.stockmate.app.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.stockmate.app.data.models.Sale;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleRepository {
    private static List<Sale> mockSales = new ArrayList<>();
    
    static {
        Sale sale1 = new Sale("1", "Coca Cola", 2, 15.00, 10.00);
        sale1.setId(UUID.randomUUID().toString());
        sale1.setSaleDate(System.currentTimeMillis() - 86400000);
        mockSales.add(sale1);
        
        Sale sale2 = new Sale("2", "Chips - Simba", 3, 10.00, 5.00);
        sale2.setId(UUID.randomUUID().toString());
        sale2.setSaleDate(System.currentTimeMillis() - 172800000);
        mockSales.add(sale2);
    }
    
    public interface SaleCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
    
    public void getAllSales(SaleCallback<List<Sale>> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(new ArrayList<>(mockSales));
        }, 500);
    }
    
    public void getTotalSales(SaleCallback<Double> callback) {
        double total = 0;
        for (Sale s : mockSales) {
            total += s.getTotalPrice();
        }
        final double finalTotal = total;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(finalTotal);
        }, 300);
    }

    public void recordSale(Sale sale, SaleCallback<String> callback) {
        sale.setId(UUID.randomUUID().toString());
        sale.setSaleDate(System.currentTimeMillis());
        mockSales.add(sale);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(sale.getId());
        }, 300);
    }
}
