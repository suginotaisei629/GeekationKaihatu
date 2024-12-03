package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.ManufacturersService;
import com.example.demo.entity.manufacturers;

@Controller
public class ManufacturersController {

    @Autowired
    private ManufacturersService manufacturersService;

    @GetMapping("/manufacturers")
    public String showManufacturerList(Model model) {
        // メーカー情報を取得（作成日時、更新日時を除外）
        List<manufacturers> manufacturers = manufacturersService.getAllManufacturers();
        
        // モデルにデータを追加
        model.addAttribute("manufacturers", manufacturers);

        return "manufacturers";  // Thymeleafテンプレート名
    }
    
    @GetMapping("/manufacturers/{id}")
    public String showManufacturerDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        manufacturers manufacturer = manufacturersService.findById(id);
        if (manufacturer == null) {
            return "error";  // メーカーが見つからない場合のエラーページ
        }

        model.addAttribute("manufacturer", manufacturer);

        // 管理者かどうかをチェックして、削除ボタンを表示するか判断
        boolean isAdmin = user != null && user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "manufacturers-id";  // 詳細画面のテンプレート名
    }
    
    @PostMapping("/manufacturers/{id}/delete")
    public String deleteManufacturer(@PathVariable Long id, @AuthenticationPrincipal User user) {
        // 管理者権限のチェック
        boolean isAdmin = user != null && user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        
        if (isAdmin) {
            manufacturersService.deleteManufacturer(id);
            return "redirect:/manufacturers";  // 削除後はメーカー一覧にリダイレクト
        }

        return "error";  // 権限がない場合はエラーページにリダイレクト
    }
    
    @GetMapping("/manufacturers/create")
    public String showCreateForm() {
        return "manufacturersNew";  // メーカー作成画面
    }
    
    @PostMapping("/manufacturers/create")
    public String createManufacturer(@RequestParam String name, @AuthenticationPrincipal User user) {
        // 新しいメーカーを作成
        manufacturers manufacturer = new manufacturers();
        manufacturer.setName(name);
        manufacturersService.saveManufacturer(manufacturer);

        return "redirect:/manufacturers";  // メーカー一覧画面にリダイレクト
    }
    
    @GetMapping("/manufacturers/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 編集するメーカーの情報を取得
        manufacturers manufacturer = manufacturersService.getManufacturerById(id);
        
        if (manufacturer == null) {
            // メーカーが見つからない場合のエラーページへの遷移
            model.addAttribute("errorMessage", "Manufacturer not found.");
            return "error";  // エラーページに遷移
        }

        // 正常に取得できた場合、メーカー情報をモデルに追加
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturers-edit";  // メーカー編集画面
    }
    
    @PostMapping("/manufacturers/{id}/edit")
    public String editManufacturer(@PathVariable Long id,@RequestParam String name, @AuthenticationPrincipal User user) {
        
        // メーカー情報を更新
        manufacturers manufacturer = manufacturersService.getManufacturerById(id);
        manufacturer.setName(name);
        manufacturersService.saveManufacturer(manufacturer);

        return "redirect:/manufacturers";  // メーカー一覧にリダイレクト
    }
}
