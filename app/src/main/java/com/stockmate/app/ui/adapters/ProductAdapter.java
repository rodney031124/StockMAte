package com.stockmate.app.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stockmate.app.R;
import com.stockmate.app.data.models.Product;
import com.stockmate.app.utils.CurrencyUtils;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products = new ArrayList<>();
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(OnProductClickListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(products.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvCategory, tvQuantity, tvBuyPrice, tvSellPrice;
        private final View viewStockIndicator;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvBuyPrice = itemView.findViewById(R.id.tv_buy_price);
            tvSellPrice = itemView.findViewById(R.id.tv_sell_price);
            viewStockIndicator = itemView.findViewById(R.id.view_stock_indicator);
        }

        public void bind(final Product product, final OnProductClickListener listener) {
            tvName.setText(product.getName());
            tvCategory.setText(product.getCategory());
            tvQuantity.setText(String.valueOf(product.getQuantity()));
            tvBuyPrice.setText("Buy: " + CurrencyUtils.format(product.getBuyingPrice()));
            tvSellPrice.setText("Sell: " + CurrencyUtils.format(product.getSellingPrice()));

            // Change color to red if low stock
            if (product.getQuantity() <= 5) {
                tvQuantity.setTextColor(Color.parseColor("#F44336"));
                viewStockIndicator.setBackgroundColor(Color.parseColor("#F44336"));
            } else {
                tvQuantity.setTextColor(Color.parseColor("#4CAF50"));
                viewStockIndicator.setBackgroundColor(Color.parseColor("#4CAF50"));
            }

            itemView.setOnClickListener(v -> listener.onProductClick(product));
        }
    }
}
