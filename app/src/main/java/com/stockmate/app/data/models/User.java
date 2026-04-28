package com.stockmate.app.data.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String email;
    private String displayName;
    private String role;
    private long createdAt;
    
    public User() {}
    
    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
        this.role = "user";
        this.createdAt = System.currentTimeMillis();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    
    public boolean isAdmin() {
        return "admin".equals(role);
    }
}