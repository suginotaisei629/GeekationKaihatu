package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table; 

@Entity
@Table(name = "purchase_history")  // テーブル名を指定
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 履歴ID (自動生成)

    @Column(name = "admin_id")  // admin_idカラムに対応
    private Long adminId;  // 管理者ID

    @Column(name = "product_id")  // product_idカラムに対応
    private Long productId;  // 商品ID

    @Column(name = "store_id")  // store_idカラムに対応
    private Long storeId;    // 店舗ID

    private int quantity;    // 発注数

    @Column(name = "total_price", precision = 10, scale = 2)  // total_priceカラムに対応
    private BigDecimal totalPrice;  // 総額

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  // created_atカラムに対応
    private LocalDateTime createdAt;  // 作成日時

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")  // updated_atカラムに対応
    private LocalDateTime updatedAt;  // 更新日時

	private LocalDateTime orderDate;
	
	@ManyToOne
    @JoinColumn(name = "users_id")
    private users user;
	
	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private ProductCategoryView ProductCategoryView;
	
	public BigDecimal getTotalAmount() {
	    if (ProductCategoryView != null && ProductCategoryView.getCostPrice() != null) {
	        // Double型のcostPriceをBigDecimalに変換して計算
	        BigDecimal costPrice = BigDecimal.valueOf(ProductCategoryView.getCostPrice());  // Double -> BigDecimal
	        return costPrice.multiply(new BigDecimal(quantity));  // quantityはintなのでBigDecimalに変換して掛け算
	    }
	    return BigDecimal.ZERO;
	}




    // Getter, Setter, コンストラクタなど省略

	public LocalDateTime getOrderDate() {
	    return orderDate;
	}
	
	
	
	
    public PurchaseHistory() {}

    public PurchaseHistory(Long productId, Long storeId, int quantity, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.storeId = storeId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;  // usersオブジェクトをセット
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now; // 保存前にupdatedAtを設定
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now(); // 更新前にupdatedAtを更新
    }

}
