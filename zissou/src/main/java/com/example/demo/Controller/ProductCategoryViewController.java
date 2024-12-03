package com.example.demo.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repository.PurchaseHistoryRepository;
import com.example.demo.Repository.kaihatuRepository;
import com.example.demo.Repository.storesRepository;
import com.example.demo.Service.ProductCategoryViewService;
import com.example.demo.Service.storesService;
import com.example.demo.entity.ProductCategoryView;
import com.example.demo.entity.PurchaseHistory;
import com.example.demo.entity.stores;
import com.example.demo.entity.users;

@Controller
public class ProductCategoryViewController {

    @Autowired
    private ProductCategoryViewService productCategoryViewService;
    
    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;
    
    @Autowired
    private kaihatuRepository kaihatuRepository;

    @Autowired
    private storesRepository storesRepository;
    
    @Autowired
    private storesService storesService;  // 店舗情報を取得するサービス
    


    // 商品カテゴリ検索の共通処理
    @GetMapping("/ProductCategoryView")
    public String showSearchPage(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "largeCategory", required = false) String largeCategory,
            @RequestParam(value = "mediumCategory", required = false) String mediumCategory,
            @RequestParam(value = "smallCategory", required = false) String smallCategory,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size, // 1ページあたりのアイテム数（デフォルトは30）
            Model model) {

        // パラメータがnullの場合、空文字列をセット
        productName = (productName != null) ? productName : "";
        largeCategory = (largeCategory != null) ? largeCategory : "";
        mediumCategory = (mediumCategory != null) ? mediumCategory : "";
        smallCategory = (smallCategory != null) ? smallCategory : "";

        // Pageableの作成
        Pageable pageable = PageRequest.of(page, size);

        // 検索クエリをサービス層に渡して、ページングされた結果を取得
        Page<ProductCategoryView> productsPage = productCategoryViewService.searchAllProducts(
                productName, largeCategory, mediumCategory, smallCategory, pageable);

        // 商品リストを取得
        List<ProductCategoryView> productList = productsPage.getContent();

        // モデルにデータを設定
        model.addAttribute("productList", productList); 
        model.addAttribute("currentPage", productsPage.getNumber() + 1); // 1始まりに調整
        model.addAttribute("totalPages", productsPage.getTotalPages());

        // 大カテゴリ・中カテゴリ・小カテゴリの選択肢を取得
        List<String> largeCategories = productCategoryViewService.getAllLargeCategories();
        List<String> mediumCategories = productCategoryViewService.getAllMediumCategories();
        List<String> smallCategories = productCategoryViewService.getAllSmallCategories();

        // カテゴリデータをモデルに追加
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("mediumCategories", mediumCategories);
        model.addAttribute("smallCategories", smallCategories);

        // 検索結果ページを返す
        return "ProductCategoryView";  // 商品検索フォームページ
    }



    
    @GetMapping("/product/{productId}")
    public String getProductDetail(@PathVariable("productId") Long productId, Model model) {
        ProductCategoryView product = productCategoryViewService.getProductById(productId);
        model.addAttribute("product", product);
        return "ProductCategoryView-id";  // 作成したテンプレート名
    }
    
    
    @GetMapping("/order/{productId}")
    public String showOrderPage(@PathVariable("productId") Long productId, Model model) {
        // 商品IDを使って商品詳細を取得
        ProductCategoryView product = productCategoryViewService.getProductById(productId);

        if (product == null) {
            throw new IllegalArgumentException("指定された商品が見つかりません");
        }
        List<stores> stores = storesService.getAllStores(); // Storeはあなたが定義したエンティティ（またはDTO）です
        
        // モデルに商品と店舗情報を追加
        model.addAttribute("product", product);
        model.addAttribute("stores", stores);  // 店舗情報を追加
        
        return "order";  // 発注画面に遷移
    }
    
    
    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("productId") Long productId,
                              @RequestParam("storeId") Long storeId,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("email") String email, // メールアドレスを受け取る
                              Model model) {
        // メールアドレスからユーザーを取得
        users user = kaihatuRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("message", "ユーザーが見つかりませんでした。");
            return "order"; // エラーメッセージを表示
        }

        // 商品情報を取得（仮）
        ProductCategoryView product = productCategoryViewService.getProductById(productId);

        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setProductId(productId);
        purchaseHistory.setStoreId(storeId); // 店舗ID
        purchaseHistory.setQuantity(quantity);
        purchaseHistory.setOrderDate(LocalDateTime.now());  // 発注日時は現在日時
        purchaseHistory.setUpdatedAt(LocalDateTime.now());

        purchaseHistory.setUser(user);
        
        // 発注履歴を保存
        purchaseHistoryRepository.save(purchaseHistory);


        // 発注完了ページへリダイレクト
        model.addAttribute("message", "発注が完了しました！");
        return "redirect:/ProductCategoryView";  // 発注完了ページ
    }


    @GetMapping("/purchase_history")
    public String showPurchaseHistory(Model model, Principal principal) {
        // ログインしているユーザーのメールアドレスを取得
        String loggedInEmail = principal.getName();

        // ユーザー情報を取得
        users user = kaihatuRepository.findByEmail(loggedInEmail);


        // ユーザーのstoreIdを取得
        Long storeId = Long.valueOf(user.getStoreId());  // IntegerをLongに変換
        Long userId = user.getId();

        // ユーザーIDとstoreIdを元に発注履歴を取得
        List<PurchaseHistory> purchaseHistories = 
        	    purchaseHistoryRepository.findUserPurchaseHistory(storeId, userId);

        // モデルに購入履歴を追加
        model.addAttribute("purchaseHistories", purchaseHistories);

        // ユーザー名を追加
        String userName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("userName", userName);

        // 購入履歴を表示するHTMLページを返す
        return "PurchaseHistory";
    }


    

    
    



    
}

