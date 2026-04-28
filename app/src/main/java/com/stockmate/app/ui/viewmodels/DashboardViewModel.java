package com.stockmate.app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.data.repository.ProductRepository;
import com.stockmate.app.data.repository.SaleRepository;
import java.util.List;

public class DashboardViewModel extends ViewModel {
    private ProductRepository productRepository;
    private SaleRepository saleRepository;
    private MutableLiveData<Integer> totalProducts = new MutableLiveData<>(0);
    private MutableLiveData<Double> totalSales = new MutableLiveData<>(0.0);
    private MutableLiveData<List<Product>> lowStockProducts = new MutableLiveData<>();
    
    public DashboardViewModel() {
        productRepository = new ProductRepository();
        saleRepository = new SaleRepository();
        loadData();
    }
    
    public LiveData<Integer> getTotalProducts() { return totalProducts; }
    public LiveData<Double> getTotalSales() { return totalSales; }
    public LiveData<List<Product>> getLowStockProducts() { return lowStockProducts; }
    
    private void loadData() {
        productRepository.getAllProducts(new ProductRepository.FirestoreCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                totalProducts.setValue(result.size());
                productRepository.getLowStockProducts(10, new ProductRepository.FirestoreCallback<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> lowStock) {
                        lowStockProducts.setValue(lowStock);
                    }
                    @Override
                    public void onFailure(Exception e) {}
                });
            }
            @Override
            public void onFailure(Exception e) {}
        });
        
        saleRepository.getTotalSales(new SaleRepository.SaleCallback<Double>() {
            @Override
            public void onSuccess(Double result) {
                totalSales.setValue(result);
            }
            @Override
            public void onFailure(Exception e) {}
        });
    }
}