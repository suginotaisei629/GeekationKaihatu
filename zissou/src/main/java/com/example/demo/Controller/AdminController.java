package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Repository.AdminRepository;
import com.example.demo.entity.users;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository AdminRepository;

    // GET リクエストで管理者一覧を表示
    @GetMapping("/admin")
    public String getAdminUsers(Model model) {
        // permissionsが'admin'のユーザーを取得
        List<users> adminUsers = AdminRepository.findByPermissions("admin");

        // 管理者一覧をビューに渡す
        model.addAttribute("admin", adminUsers);

        return "admin";  // 管理者一覧のテンプレート
    }
    
    @GetMapping("/admin/detail/{id}")
    public String viewAdminDetail(@PathVariable Long id, Model model) {
        Optional<users> user = AdminRepository.findById(id);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            // 現在のユーザーの権限が「admin」であるかどうかをチェック
            boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("hasAdminRole", hasAdminRole);
            return "admin-id";  // admin_detail.htmlにデータを渡す
        } else {
            return "redirect:/admin";  // ユーザーが見つからない場合、管理者一覧にリダイレクト
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        Optional<users> user = AdminRepository.findById(id);
        if (user.isPresent()) {
            AdminRepository.delete(user.get());
        }
        return "redirect:/admin";  // 削除後、管理者一覧にリダイレクト
    }
}
