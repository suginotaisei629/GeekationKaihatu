package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id; // Jakarta Persistence の場合
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class stores {
	 @Id 
	 private Long Id; // 複合主キー

 
    private String fname;  // 店舗名

    @Column(name = "address")
    private String address;  // 住所

    @Column(name = "sale_price")
    private Double salePrice;  // 店舗での販売価格

    @Column(name = "stock_quantity")
    private Integer stockQuantity;  // 店舗での在庫数
    
    @Column(name = "store_details")
    private String storeDetails;  // 店舗の詳細情報（新しく追加）

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private ProductCategoryView productCategoryView;  // 商品と店舗の関連

    public String getStoreDetails() {
        return storeDetails;
    }

    public void setStoreDetails(String storeDetails) {
        this.storeDetails = storeDetails;
    }

    
    // 店舗名のgetterとsetter
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }
    
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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

    public void setStockQuantity(Integer newStockQuantity) {
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("在庫数は負の値にはできません");
        }
        this.stockQuantity = newStockQuantity;
    }

    public ProductCategoryView getProductCategoryView() {
        return productCategoryView;
    }

    public void setProductCategoryView(ProductCategoryView productCategoryView) {
        this.productCategoryView = productCategoryView;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    
    // コンストラクタ
    public stores() {
    }

    @Override
    public String toString() {
        return "stores [Id=" + Id + ", fname=" + fname + ", salePrice=" + salePrice 
                + ", stockQuantity=" + stockQuantity + "]";
    }
}


