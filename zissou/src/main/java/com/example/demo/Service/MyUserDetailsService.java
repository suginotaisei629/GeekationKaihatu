package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.kaihatuRepository;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.entity.users;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
    private  kaihatuRepository kaihatuRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // データベースからユーザー情報を取得
        users user = kaihatuRepository.findByEmail(email);
        
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
        }
        
        
       String permission = user.getPermissions();
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if ("admin".equalsIgnoreCase(permission)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
        	throw new UsernameNotFoundException("管理者権限が必要です: " + email);  // "user" ロールの場合、例外をスロー
        }

         
        
        // 取得したユーザー情報から、UserDetailsを作成
        return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities, user.getStoreId(), user);
    }
}
