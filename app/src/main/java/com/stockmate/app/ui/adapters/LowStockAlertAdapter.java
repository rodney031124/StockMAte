package com.stockmate.app.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Product;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LowStockAlertAdapter extends RecyclerView.Adapter<LowStockAlertAdapter.ViewHolder> {
    private List<Product> products = new ArrayList<>();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_low_stock_alert, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvCategory.setText(product.getCategory());
        holder.tvQuantity.setText("Stock: " + product.getQuantity());
        holder.tvPrice.setText(currencyFormat.format(product.getSellingPrice()));
    }
    
    @Override
    public int getItemCount() { return products.size(); }
    
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvCategory, tvQuantity, tvPrice;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}