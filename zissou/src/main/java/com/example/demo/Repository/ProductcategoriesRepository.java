package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductCategory;

@Repository
public interface ProductcategoriesRepository extends JpaRepository<ProductCategory, Long> {

    // 大カテゴリ一覧を取得する
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.level = 0")
    List<ProductCategory> findLargeCategories();
    
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.parentId = ?1 AND pc.level = 1")
    List<ProductCategory> findByParentId(Long parentId);
    
    
    List<ProductCategory> findByParentIdAndLevel(Long parentId, int level);
}

