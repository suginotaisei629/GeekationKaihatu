package com.example.demo.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_category_view_modified")
public class ProductCategoryView {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "small_category_name")
    private String smallCategoryName;

    @Column(name = "medium_category_name")
    private String mediumCategoryName;

    @Column(name = "large_category_name")
    private String largeCategoryName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "retail_price")
    private Double retailPrice;

    @Column(name = "manufacturer")
    private String manufacturer;
    
    @Column(name = "sale_price")
    private Double salePrice;
    
    @Column(name = "stock_quantity") // データベースにカラムがあれば
    private Integer stockQuantity;

    // 店舗情報を関連付けるリスト
    @OneToMany(mappedBy = "productCategoryView", fetch = FetchType.EAGER)
    private List<stores> stores;
    
    @Column(name = "store_id")
    private Long storeId;  // store_idフィールドを追加

    
    private String storeDetails;  // 店舗情報の集計結果用
    
    
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    // 商品IDのgetterとsetter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // 商品名のgetterとsetter
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // その他の属性のgetterとsetter
    public String getSmallCategoryName() {
        return smallCategoryName;
    }

    public void setSmallCategoryName(String smallCategoryName) {
        this.smallCategoryName = smallCategoryName;
    }

    public String getMediumCategoryName() {
        return mediumCategoryName;
    }

    public void setMediumCategoryName(String mediumCategoryName) {
        this.mediumCategoryName = mediumCategoryName;
    }

    public String getLargeCategoryName() {
        return largeCategoryName;
    }

    public void setLargeCategoryName(String largeCategoryName) {
        this.largeCategoryName = largeCategoryName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    // stores（店舗情報）のgetterとsetter
    public List<stores> getStores() {
        return stores;
    }

    public void setStores(List<stores> stores) {
        this.stores = stores;
    }

    // 店舗情報（storeDetails）のgetterとsetter
    public String getStoreDetails() {
        if (stores != null) {
            StringBuilder details = new StringBuilder();
            for (stores store : stores) {
                details.append("店舗名: ").append(store.getFname())
                        .append(", 価格: ").append(store.getSalePrice())
                        .append(", 在庫数: ").append(store.getStockQuantity())
                        .append(" | ");
            }
            return details.toString();
        }
        return "";
    }

    // 店舗情報をまとめて設定するためのsetter
    public void setStoreDetails(String storeDetails) {
        this.storeDetails = storeDetails;
    }

    public int getStockQuantity() {
        // storesリストの在庫数を集計して返す
        int totalStock = 0;
        if (stores != null) {
            for (stores store : stores) {
                totalStock += store.getStockQuantity();  // 各店舗の在庫数を合計
            }
        }
        return totalStock;
    }
    
 // 商品に関連するすべての店舗の在庫数を設定する
    public void setStockQuantity(int newStockQuantity) {
        if (stores != null) {
            for (stores store : stores) {
                // 各店舗の在庫数を個別に設定
                store.setStockQuantity(newStockQuantity);  // 各店舗に対して在庫数を更新
            }
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductCategoryView that = (ProductCategoryView) obj;
        return productId == that.productId;  // product_id のみで比較
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    
    // コンストラクタ
    public ProductCategoryView() {
    }

    @Override
    public String toString() {
        return "ProductCategoryView [productId=" + productId + ", productName=" + productName
                + ", smallCategoryName=" + smallCategoryName + ", mediumCategoryName=" + mediumCategoryName
                + ", largeCategoryName=" + largeCategoryName + ", productDescription=" + productDescription
                + ", imageUrl=" + imageUrl + ", costPrice=" + costPrice + ", retailPrice=" + retailPrice
                + ", manufacturer=" + manufacturer + "]";
    }

}

