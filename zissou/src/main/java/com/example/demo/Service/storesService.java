package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.storesRepository;
import com.example.demo.entity.stores;

@Service
public class storesService {

    @Autowired
    private storesRepository storesRepository;

    // すべての店舗を取得
    public Optional<stores> findByFname(String fname) {
        return storesRepository.findFirstByFname(fname); // 最初の1件を取得
    }
    
    public stores findById(Long id) {
        return storesRepository.findById(id).orElse(null);  // 店舗IDにマッチする店舗を検索
    }
    public void save(stores store) {
        storesRepository.save(store);
    }
    
    public stores saveStore(stores store) {
        return storesRepository.save(store);  // 保存されたstoreオブジェクトを返す
    }
    
    public List<stores> getAllStores() {
        return storesRepository.findAll();  // 全店舗情報を取得
    }
}
