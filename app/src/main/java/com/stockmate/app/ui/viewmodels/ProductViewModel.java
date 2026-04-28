package com.stockmate.app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.data.repository.ProductRepository;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public ProductViewModel() {
        this.repository = new ProductRepository();
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadProducts() {
        isLoading.setValue(true);
        repository.getAllProducts(new ProductRepository.FirestoreCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                products.setValue(result);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Exception e) {
                isLoading.setValue(false);
            }
        });
    }
}
