package com.example.demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ProductCategoryViewRepository;
import com.example.demo.Repository.storesRepository;
import com.example.demo.dto.ProductCategoryViewDTO;
import com.example.demo.entity.ProductCategoryView;

@Service
public class ProductCategoryViewService {

    @Autowired
    private ProductCategoryViewRepository productCategoryViewRepository;
    
    @Autowired
    private storesRepository storesRepository; // StoreRepositoryを追加

    // 商品名やカテゴリを基に検索
    public Page<ProductCategoryView> searchAllProducts(String productName, 
            String largeCategoryName, 
            String mediumCategoryName, 
            String smallCategoryName, Pageable pageable) {
    	Page<ProductCategoryView> productsPage = productCategoryViewRepository.getProductStoreDetails(
                productName, largeCategoryName, mediumCategoryName, smallCategoryName, pageable);

    	List<ProductCategoryView> uniqueProducts = productsPage.getContent().stream()
    	        .collect(Collectors.toMap(
    	            ProductCategoryView::getProductName,  // 商品名で重複を排除
    	            product -> product,
    	            (existing, replacement) -> existing))  // 重複時は最初のものを選択
    	        .values()
    	        .stream()
    	        .collect(Collectors.toList());
    	
    	return new PageImpl<>(uniqueProducts, pageable, productsPage.getTotalElements());
    }


    
    public ProductCategoryView getProductById(Long productId) {
    	if (productId == null) {
            throw new IllegalArgumentException("IDはnullではいけません");
        }
    	return productCategoryViewRepository.findById(productId)
    		    .orElseThrow(() -> new EntityNotFoundException("指定されたIDの商品カテゴリは存在しません"));

    }

    // 大カテゴリのリストを取得
    public List<String> getAllLargeCategories() {
        return productCategoryViewRepository.findAllLargeCategories();
    }

    // 中カテゴリのリストを取得
    public List<String> getAllMediumCategories() {
        return productCategoryViewRepository.findAllMediumCategories();
    }

    // 小カテゴリのリストを取得
    public List<String> getAllSmallCategories() {
        return productCategoryViewRepository.findAllSmallCategories();
    }
    
    public List<ProductCategoryViewDTO> getCategoryProductDetails(String productName,
            String largeCategoryName, String mediumCategoryName, String smallCategoryName) {

    List<ProductCategoryView> products = productCategoryViewRepository.findProductsByCategories(
            productName, largeCategoryName, mediumCategoryName, smallCategoryName);

    // ProductCategoryViewからDTOへ変換
    return products.stream()
            .map(product -> new ProductCategoryViewDTO(product)) // ProductCategoryViewオブジェクトを渡す
            .collect(Collectors.toList());
}
    
    public ProductCategoryViewDTO getProductDetailsById(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("IDはnullではいけません");
        }

        ProductCategoryView product = productCategoryViewRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("指定されたIDの商品カテゴリは存在しません"));

        // ProductCategoryViewからDTOへ変換して返す
        return new ProductCategoryViewDTO(product);


    }

}
