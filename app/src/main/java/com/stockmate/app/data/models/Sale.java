package com.stockmate.app.data.models;

import java.io.Serializable;

public class Sale implements Serializable {
    private String id;
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private double profit;
    private long saleDate;
    
    public Sale() {}
    
    public Sale(String productId, String productName, int quantity, double unitPrice, double profit) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
        this.profit = profit;
        this.saleDate = System.currentTimeMillis();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    public double getProfit() { return profit; }
    public void setProfit(double profit) { this.profit = profit; }
    
    public long getSaleDate() { return saleDate; }
    public void setSaleDate(long saleDate) { this.saleDate = saleDate; }
}