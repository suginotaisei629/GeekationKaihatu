package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurity {


    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf() // CSRF保護を明示的に有効化
        .and()
            .formLogin()
                .loginPage("/login")  // ログインページURL
                .loginProcessingUrl("/login")  // ログイン処理URL
                .defaultSuccessUrl("/top", true) 
                .permitAll()
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/login").hasRole("ADMIN") 
                .anyRequest().authenticated(); 
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // パスワードのハッシュ化方式として BCrypt を使用
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        // userDetailsService を使用
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                                     .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
