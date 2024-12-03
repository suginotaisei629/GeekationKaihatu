package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.kaihatuRepository;
import com.example.demo.entity.users;

@Service
public class kaihatuService{

    @Autowired
    private  kaihatuRepository kaihatuRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  

    public void save(users user) {
        // パスワードをハッシュ化
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);  // ハッシュ化したパスワードをセット

        // ユーザー情報をデータベースに保存
        kaihatuRepository.save(user);  // 保存
    }
    
    public users findByEmail(String email) {
        return kaihatuRepository.findByEmail(email);
    }

    // IDでユーザーを検索
    public users findById(Long id) {
        return kaihatuRepository.findById(id).orElse(null);
    }
}
