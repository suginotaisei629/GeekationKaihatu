package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.ProductCategoryViewService;
import com.example.demo.dto.ProductCategoryViewDTO;

@RestController
@RequestMapping("/api/products")
public class ProductCategoryViewApiController {

    @Autowired
    private ProductCategoryViewService productCategoryViewService; // Service層を利用

    // 商品リストを取得するAPIエンドポイント
    @GetMapping
    public ResponseEntity<List<ProductCategoryViewDTO>> getCategoryProductDetails(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "largeCategoryName", required = false) String largeCategoryName,
            @RequestParam(value = "mediumCategoryName", required = false) String mediumCategoryName,
            @RequestParam(value = "smallCategoryName", required = false) String smallCategoryName) {

        List<ProductCategoryViewDTO> products = productCategoryViewService.getCategoryProductDetails(
                productName, largeCategoryName, mediumCategoryName, smallCategoryName);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products); // 結果が空の場合
        }
        return ResponseEntity.ok(products); // 正常に商品リストが取得できた場合
    }
    
    // 商品詳細を取得するAPIエンドポイント
    @GetMapping("/{productId}")
    public ResponseEntity<ProductCategoryViewDTO> getProductDetails(@PathVariable Long productId) {
        ProductCategoryViewDTO productDTO = productCategoryViewService.getProductDetailsById(productId);

        if (productDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 商品が見つからない場合、404を返す
        }

        return ResponseEntity.ok(productDTO);
    }
}
