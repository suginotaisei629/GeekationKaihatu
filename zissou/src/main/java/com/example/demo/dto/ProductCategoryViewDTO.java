package com.example.demo.dto;

import com.example.demo.entity.ProductCategoryView;

public class ProductCategoryViewDTO {
    private String largeCategoryName;
    private String mediumCategoryName;
    private String smallCategoryName;
    private String productName;
    private String description;
    private double costPrice;  // purchaseCost を costPrice に修正
    private double retailPrice;  // suggestedRetailPrice を retailPrice に修正

    // コンストラクタ: エンティティからDTOを生成
    public ProductCategoryViewDTO(ProductCategoryView product) {
        this.largeCategoryName = product.getLargeCategoryName();
        this.mediumCategoryName = product.getMediumCategoryName();
        this.smallCategoryName = product.getSmallCategoryName();
        this.productName = product.getProductName();
        this.description = product.getProductDescription(); // 修正
        this.costPrice = product.getCostPrice();         // 修正
        this.retailPrice = product.getRetailPrice(); // 修正
    }

    // ゲッターとセッター
    public String getLargeCategoryName() {
        return largeCategoryName;
    }

    public void setLargeCategoryName(String largeCategoryName) {
        this.largeCategoryName = largeCategoryName;
    }

    public String getMediumCategoryName() {
        return mediumCategoryName;
    }

    public void setMediumCategoryName(String mediumCategoryName) {
        this.mediumCategoryName = mediumCategoryName;
    }

    public String getSmallCategoryName() {
        return smallCategoryName;
    }

    public void setSmallCategoryName(String smallCategoryName) {
        this.smallCategoryName = smallCategoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }
}
