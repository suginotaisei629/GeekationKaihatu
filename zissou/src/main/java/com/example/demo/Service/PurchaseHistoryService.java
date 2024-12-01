package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.PurchaseHistoryRepository;
import com.example.demo.dto.PurchaseHistoryDTO;
import com.example.demo.entity.PurchaseHistory;

@Service
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository repository;

    @Autowired 
    public PurchaseHistoryService(PurchaseHistoryRepository repository) {
        this.repository = repository;
    }

    public List<PurchaseHistory> getAllPurchaseHistories() {
        return repository.findAll(); // 必要ならカスタムクエリを追加
    }
    
    public List<PurchaseHistoryDTO> getPurchaseHistory() {
        return repository.findAllWithDetails(); // DTOを直接返す
    }
    
}
