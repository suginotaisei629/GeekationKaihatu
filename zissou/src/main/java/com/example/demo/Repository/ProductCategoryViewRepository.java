package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.ProductCategoryView;
import com.example.demo.entity.stores;

public interface ProductCategoryViewRepository extends JpaRepository<ProductCategoryView, Long> {

	@Query("SELECT DISTINCT p FROM ProductCategoryView p " +
            "JOIN p.stores s " +
            "WHERE (:productName IS NULL OR p.productName LIKE %:productName%) " +
            "AND (:largeCategoryName IS NULL OR p.largeCategoryName LIKE %:largeCategoryName%) " +
            "AND (:mediumCategoryName IS NULL OR p.mediumCategoryName LIKE %:mediumCategoryName%) " +
            "AND (:smallCategoryName IS NULL OR p.smallCategoryName LIKE %:smallCategoryName%)")

	Page<ProductCategoryView> getProductStoreDetails(
	        @Param("productName") String productName,
	        @Param("largeCategoryName") String largeCategoryName,
	        @Param("mediumCategoryName") String mediumCategoryName,
	        @Param("smallCategoryName") String smallCategoryName,
	        Pageable pageable);


	
    // 大カテゴリ一覧を取得
    @Query("SELECT DISTINCT p.largeCategoryName FROM ProductCategoryView p")
    List<String> findAllLargeCategories();

    // 中カテゴリ一覧を取得
    @Query("SELECT DISTINCT p.mediumCategoryName FROM ProductCategoryView p")
    List<String> findAllMediumCategories();

    // 小カテゴリ一覧を取得
    @Query("SELECT DISTINCT p.smallCategoryName FROM ProductCategoryView p")
    List<String> findAllSmallCategories();
    
    ProductCategoryView findByProductIdAndStoresId(Long productId, Long storeId);
    
 // 商品IDに基づいて関連する店舗情報を取得するメソッド
    @Query("SELECT s FROM stores s WHERE s.productCategoryView.productId = :productId")
    List<stores> findStoresByProductId(@Param("productId") Long productId);
    
    @Query("SELECT p FROM ProductCategoryView p")
    Page<ProductCategoryView> findAllProducts(Pageable pageable);
    
    @Query("SELECT p FROM ProductCategoryView p " +
    	       "WHERE (:productName IS NULL OR p.productName LIKE %:productName%) " +
    	       "AND (:largeCategoryName IS NULL OR p.largeCategoryName LIKE %:largeCategoryName%) " +
    	       "AND (:mediumCategoryName IS NULL OR p.mediumCategoryName LIKE %:mediumCategoryName%) " +
    	       "AND (:smallCategoryName IS NULL OR p.smallCategoryName LIKE %:smallCategoryName%)")
    List<ProductCategoryView> findProductsByCategories(String productName, String largeCategoryName,
            String mediumCategoryName, String smallCategoryName);


}
