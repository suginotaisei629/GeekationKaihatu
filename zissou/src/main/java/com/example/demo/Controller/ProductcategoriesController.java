package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Repository.ProductcategoriesRepository;
import com.example.demo.entity.ProductCategory;

@Controller
@RequestMapping("/ProductCategories")
public class ProductcategoriesController {

    @Autowired
    private ProductcategoriesRepository productCategoriesRepository;

    // 大カテゴリ一覧を表示するエンドポイント
    @GetMapping
    public String showLargeCategories(Model model) {
        // 大カテゴリ一覧を取得
        List<ProductCategory> largeCategories = productCategoriesRepository.findLargeCategories();
        model.addAttribute("largeCategories", largeCategories);
        return "largeCategoryList"; // Thymeleafテンプレート名
    }
    
    @GetMapping("/{id}")
    public String showCategoryDetail(@PathVariable("id") Long id, Model model) {
        // 大カテゴリの詳細を取得
        ProductCategory largeCategory = productCategoriesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

        // 中カテゴリを取得
        List<ProductCategory> subCategories = productCategoriesRepository.findByParentId(id);
        
        model.addAttribute("largeCategory", largeCategory);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("parentId", id); // parentId を明示的にセット
        
        return "largeCategoryDetail"; // Thymeleafテンプレート名
    }
    
    @GetMapping("/subCategory/{parentId}/{id}")
    public String showSubCategoryDetail(@PathVariable("parentId") Long parentId, @PathVariable("id") Long id, Model model) {
        // 中カテゴリの詳細を取得
        ProductCategory subCategory = productCategoriesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid subcategory Id:" + id));

        // 小カテゴリ (level = 2) の取得
        List<ProductCategory> smallCategories = productCategoriesRepository.findByParentIdAndLevel(id, 2);
        
        model.addAttribute("subCategory", subCategory);
        model.addAttribute("smallCategories", smallCategories);
        return "subCategoryDetail"; // Thymeleafテンプレート名
    }
    
    
    @GetMapping("/{parentId}/{id}")
    public String showSmallCategoryDetail(@PathVariable("parentId") Long parentId, 
                                          @PathVariable("id") Long id, 
                                          Model model) {
        // 小カテゴリの詳細を取得
        ProductCategory smallCategory = productCategoriesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category Id: " + id));


        model.addAttribute("smallCategory", smallCategory);

        return "smallCategoryDetail"; // 小カテゴリ詳細ページのThymeleafテンプレート名
    }


}
