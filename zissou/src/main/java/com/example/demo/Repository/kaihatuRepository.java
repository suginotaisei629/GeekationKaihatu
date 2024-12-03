package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.users;


public interface kaihatuRepository extends JpaRepository<users, Long> {
    // メールアドレスで管理者を検索
    users findByEmail(String email);
}
