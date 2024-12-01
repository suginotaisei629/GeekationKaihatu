package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.PurchaseHistoryDTO;
import com.example.demo.entity.PurchaseHistory;
import com.example.demo.entity.users;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
	
	@Query("SELECT ph FROM PurchaseHistory ph WHERE ph.storeId = :storeId AND ph.user.Id = :userId")
	List<PurchaseHistory> findUserPurchaseHistory(@Param("storeId") Long storeId, @Param("userId") Long userId);

    // 必要に応じて履歴に関するクエリを追加できます
	List<PurchaseHistory> findAllByStoreId(Integer storeId);
	
	List<PurchaseHistory> findAllByStoreIdAndOrderDateBetween(Long storeId, LocalDateTime startDate, LocalDateTime endDate);
	
	List<PurchaseHistory> findByUserId(Long userId); 
	
	List<PurchaseHistory> findByUser(users user);
	
	List<PurchaseHistory> findAll();
	
	List<PurchaseHistory> findByStoreId(Long storeId);
	
	@Query("SELECT DISTINCT new com.example.demo.dto.PurchaseHistoryDTO(" +
	           "ph.id, " +  // 注文ID
	           "pcv.productName, " +  // 商品名
	           "CONCAT(u.firstName, ' ', u.lastName), " +  // 管理者名
	           "ph.quantity, " +  // 発注数
	           "COALESCE(ph.totalPrice, ph.quantity * pcv.salePrice), " +  // 総額（totalPriceがnullの場合はquantity * salePriceを使用）
	           "pcv.salePrice, " +  // 各店舗の販売価格
	           "pcv.stockQuantity," + // 各店舗の在庫数
	           "s.fname, " +
	           "s.address) " + 
	           "FROM PurchaseHistory ph " +
	           "LEFT JOIN ph.user u " +  // ユーザー情報の結合
	           "LEFT JOIN ph.ProductCategoryView pcv " +  // 商品カテゴリビューの結合
	           "LEFT JOIN stores s ON s.Id = ph.storeId")
	List<PurchaseHistoryDTO> findAllWithDetails();

}
