package com.example.demo.Controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.storesService;
import com.example.demo.entity.stores;



@Controller
public class storesController {

 
   @Autowired
   private storesService storesService;
   
   
    @GetMapping("/stores")
    public String showStores(Model model) {
        return "stores";  // stores.html にデータを渡して表示
    }
    
    @PostMapping("/stores")
    public String searchProfile(@RequestParam String fname,
                                Model model) {
        // ユーザー情報を検索する
    	Optional<stores> storeOptional = storesService.findByFname(fname);

    	if (storeOptional.isPresent()) {
            stores store = storeOptional.get();
            model.addAttribute("store", store);  // 店舗情報をモデルに追加
            return "redirect:/stores-id/" + store.getId();  // 詳細ページにリダイレクト
        } else {
            model.addAttribute("message", "店舗が見つかりませんでした。");
            return "stores";  // 店舗が見つからなければ、再度検索フォームを表示
        }
    }
    
    @GetMapping("/stores-id/{id}")
    public String showStoreDetail(@PathVariable Long id, Model model) {
        stores store = storesService.findById(id); // 店舗IDで店舗情報を取得

        if (store != null) {
            model.addAttribute("store", store);  // 店舗情報をモデルに追加
            return "stores-id";  // 店舗詳細ページを表示するためのテンプレート
        } else {
            model.addAttribute("message", "店舗が見つかりませんでした。");
            return "stores";  // 店舗が見つからなければ、店舗検索ページに戻る
        }
    }
    
    @PostMapping("/stores-id/{id}")
    public String updateStore(@PathVariable Long id,
                              @RequestParam String fname,
                              @RequestParam String address,
                              Model model) {
        stores store = storesService.findById(id); // 店舗IDで店舗情報を取得

        if (store != null) {
            // 店舗情報の更新
            store.setFname(fname);   // 店舗名の更新
            store.setAddress(address); // 住所の更新

            storesService.save(store); // 保存

            model.addAttribute("store", store);  // 更新後の店舗情報をモデルに追加
            return "redirect:/stores-id/" + store.getId(); // 更新後、店舗詳細ページにリダイレクト
        } else {
            model.addAttribute("message", "店舗が見つかりませんでした。");
            return "stores";  // 店舗が見つからなければ、店舗検索ページに戻る
        }
    }
    
    @GetMapping("/store-id/{id}/edit")
	public String editStoreProfile(@PathVariable Long id, Model model) {
	    stores existingStore = storesService.findById(id);
	    if (existingStore != null) {
	        model.addAttribute("existingStore", existingStore);  // ユーザー情報をモデルに追加
	        return "stores-edit";  // 編集ページ（フォーム）を表示
	    } else {
	        model.addAttribute("message", "ユーザーが見つかりませんでした。");
	        return "stores";  // ユーザーが見つからなければ、エラーページを表示
	    }
	}
	
	@PostMapping("/store-id/{id}/edit")
    public String saveStoreProfile(@PathVariable Long id,
                                    @RequestParam String fname,
                                    @RequestParam String address,
                                  Model model) {
        // ユーザー情報を更新
        stores existingStore = storesService.findById(id);
        if (existingStore != null) {
        	existingStore.setFname(fname);
        	existingStore.setAddress(address);;
        
        
        
        storesService.save(existingStore);

        model.addAttribute("store", existingStore);  // 更新されたユーザー情報を再度モデルに渡す
        return "redirect:/stores-id/"+ existingStore.getId();  // 詳細ページにリダイレクト
    } else {
        model.addAttribute("message", "ユーザーが見つかりませんでした。");
        return "stores";  // ユーザーが見つからなければ、エラーページを表示
    }   // 詳細ページにリダイレクト
    }
}
