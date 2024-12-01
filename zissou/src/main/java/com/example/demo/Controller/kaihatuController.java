package com.example.demo.Controller;



import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.kaihatuService;
import com.example.demo.entity.users;
import com.example.demo.model.kaihatuLogin;

@Controller
public class kaihatuController {
	@Autowired
	private kaihatuService kaihatuService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
    // ログインページ表示
	@GetMapping("/login")
    public String loginPage(Model model) {
		model.addAttribute("kaihatuLogin", new kaihatuLogin());
		return "login"; 
	}
	
	@GetMapping("/top")
    public String adminHomePage(Model model) {
        return "top"; // top.htmlというテンプレートに遷移
    }
	
	
	@GetMapping("/new")
    public String showRegisterForm() {
        return "new";  // 
    }
	
	@PostMapping("/new")
    public String registerUser(@RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String role,
                                @RequestParam(required = false) String permissions,
                                @RequestParam(required = false) Integer storeId,
                                Model model) {

        // 管理者オブジェクトを作成
		users user = new users();  
	    user.setLastName(lastName);
	    user.setFirstName(firstName);
	    user.setEmail(email);  // Email（usernameとして扱う場合）
	    user.setPassword(password);  // パスワードを暗号化
	    user.setRole(role);  // 役職情報（管理者、一般ユーザーなど）
	    user.setPermissions(permissions);  // 権限ID（必要な場合）
	    user.setStoreId(storeId);  // 任意で店舗IDを設定
	    user.setCreatedAt(LocalDateTime.now());  // 作成日時を設定
	    user.setUpdatedAt(LocalDateTime.now());  // 更新日時も設定

        // 管理者をDBに保存
        kaihatuService.save(user);

        // トップページにリダイレクト
        return "redirect:/top";  // top.htmlにリダイレクト
    }
	
	@GetMapping("/profile")
	public String showProfilePage(Model model) {
	    return "profile"; // "profile.html" テンプレートを返す
	}
	
	@PostMapping("/profile")
    public String searchProfile(@RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String email,
                                Model model) {
        // ユーザー情報を検索する
        users user = kaihatuService.findByEmail(email);

        if (user != null) {
            model.addAttribute("user", user);  // ユーザー情報をモデルに追加
            return "redirect:/profile-id/" + user.getId();  // 詳細ページにリダイレクト
        } else {
            model.addAttribute("message", "ユーザーが見つかりませんでした。");
            return "profile";  // ユーザーが見つからなければ、再度検索フォームを表示
        }
    }
	
	@GetMapping("/profile-id/{id}")
    public String showProfileDetail(@PathVariable Long id, Model model) {
        users user = kaihatuService.findById(id);

        if (user != null) {
            model.addAttribute("user", user);  // ユーザー情報をモデルに追加
            return "profile-id";  // 詳細ページにユーザー情報を渡す
        } else {
            model.addAttribute("message", "ユーザーが見つかりませんでした。");
            return "profile";  // ユーザーが見つからなければ、検索フォームにリダイレクト
        }
    }
	
	@PostMapping("/profile-id/{id}")
	public String updateProfile(@PathVariable Long id, 
	                            @RequestParam String lastName,
	                            @RequestParam String firstName,
	                            @RequestParam String username,
	                            @RequestParam String role,
	                            @RequestParam String permissions,
	                            Model model) {
	    // ユーザー情報を更新する処理
	    users user = kaihatuService.findById(id);
	    if (user != null) {
	        user.setLastName(lastName);
	        user.setFirstName(firstName);
	        user.setEmail(username); 
	        user.setRole(role);
	        user.setPermissions(permissions);

	        kaihatuService.save(user);  // 更新されたユーザー情報を保存

	        model.addAttribute("user", user);
	        return "redirect:/profile-id"+ user.getId();  // 更新されたユーザー情報を表示するページに遷移
	    } else {
	        model.addAttribute("message", "ユーザーが見つかりませんでした。");
	        return "profile";  // ユーザーが見つからなければ、エラーメッセージを表示
	    }
	}
	
	@GetMapping("/profile-id/{id}/edit")
	public String editUserProfile(@PathVariable Long id, Model model) {
	    users existingUser = kaihatuService.findById(id);
	    if (existingUser != null) {
	        model.addAttribute("existingUser", existingUser);  // ユーザー情報をモデルに追加
	        return "profile-edit";  // 編集ページ（フォーム）を表示
	    } else {
	        model.addAttribute("message", "ユーザーが見つかりませんでした。");
	        return "profile";  // ユーザーが見つからなければ、エラーページを表示
	    }
	}
	
	@PostMapping("/profile-id/{id}/edit")
    public String saveUserProfile(@PathVariable Long id, 
                                  @RequestParam String lastName,
                                  @RequestParam String firstName,
                                  @RequestParam String email,
                                  @RequestParam(required = false) String password,
                                  @RequestParam String role,
                                  @RequestParam String permissions,
                                  Model model) {
        // ユーザー情報を更新
        users existingUser = kaihatuService.findById(id);
        if (existingUser != null) {
        	existingUser.setLastName(lastName);
        	existingUser.setFirstName(firstName);
        	existingUser.setEmail(email);
        	existingUser.setRole(role);
        	existingUser.setPermissions(permissions);
        
        if (password != null && !password.isEmpty()) {
        	existingUser.setPassword(passwordEncoder.encode(password));
        	// パスワードをハッシュ化して保存
        }
        
        kaihatuService.save(existingUser);

        model.addAttribute("user", existingUser);  // 更新されたユーザー情報を再度モデルに渡す
        return "redirect:/profile-id/"+ existingUser.getId();  // 詳細ページにリダイレクト
    } else {
        model.addAttribute("message", "ユーザーが見つかりませんでした。");
        return "profile";  // ユーザーが見つからなければ、エラーページを表示
    }   // 詳細ページにリダイレクト
    }
	
	
}

