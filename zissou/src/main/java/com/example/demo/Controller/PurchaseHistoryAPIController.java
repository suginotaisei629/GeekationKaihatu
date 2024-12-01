package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.PurchaseHistoryService;
import com.example.demo.dto.PurchaseHistoryDTO;

@RestController
@RequestMapping("/api/purchase-history")
public class PurchaseHistoryAPIController {

    private final PurchaseHistoryService purchaseHistoryService;

    // コンストラクタ名を修正
    @Autowired
    public PurchaseHistoryAPIController(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    // 購入履歴を取得するAPIエンドポイント
    @GetMapping
    public ResponseEntity<List<PurchaseHistoryDTO>> getPurchaseHistory() {
        List<PurchaseHistoryDTO> purchaseHistory = purchaseHistoryService.getPurchaseHistory();
        if (purchaseHistory.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // データがない場合
        }
        return new ResponseEntity<>(purchaseHistory, HttpStatus.OK); // 200 OK とデータを返す
    }
}
