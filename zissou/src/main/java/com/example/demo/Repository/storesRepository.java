package com.example.demo.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.stores;

public interface storesRepository extends JpaRepository<stores, Long> {
    // 必要なクエリメソッドをここに定義できます
	Optional<stores> findFirstByFname(String fname);  // Optionalで最初の1件を取得
	
	 List<stores> findAll(); // 店舗リストを取得
	 
	 stores findByFname(String storeName); 
	 
	 Optional<stores> findById(Long Id);
}