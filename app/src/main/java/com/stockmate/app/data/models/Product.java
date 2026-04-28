package com.stockmate.app.data.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String category;
    private double buyingPrice;
    private double sellingPrice;
    private int quantity;
    private String description;
    private long createdAt;
    
    public Product() {}
    
    public Product(String name, String category, double sellingPrice, int quantity, String description) {
        this.name = name;
        this.category = category;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.description = description;
        this.createdAt = System.currentTimeMillis();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public double getBuyingPrice() { return buyingPrice; }
    public void setBuyingPrice(double buyingPrice) { this.buyingPrice = buyingPrice; }
    
    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    
    public double getProfit() {
        return sellingPrice - buyingPrice;
    }
    
    public boolean isLowStock() {
        return quantity < 10;
    }
}