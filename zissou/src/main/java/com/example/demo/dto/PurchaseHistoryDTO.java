package com.example.demo.dto;

import java.math.BigDecimal;

public class PurchaseHistoryDTO {

    private Long orderId;        // 注文ID
    private String productName;  // 商品名
    private String adminName;    // 管理者名
    private int quantity;        // 発注数
    private BigDecimal totalPrice; // 総額
    private Double salePrice;      // 各店舗の販売価格
    private Integer stockQuantity; // 各店舗の在庫数
    private String storeName;     // 店舗名
    private String storeAddress;  // 店舗住所

    // コンストラクタ
    public PurchaseHistoryDTO(Long orderId, String productName, String adminName, int quantity, BigDecimal totalPrice, Double salePrice, Integer stockQuantity, String storeName, String storeAddress) {
        this.orderId = orderId;
        this.productName = productName;
        this.adminName = adminName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }

    // Getter and Setter
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }


    @Override
    public String toString() {
        return "PurchaseHistoryDTO{" +
                "orderId=" + orderId +
                ", productName='" + productName + '\'' +
                ", adminName='" + adminName + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", salePrice=" + salePrice +
                ", stockQuantity=" + stockQuantity +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                '}';
    }
}
