package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.users;

public interface AdminRepository extends JpaRepository<users, Long> {
    // permissionsが指定された値のユーザーをリストで取得
    List<users> findByPermissions(String permissions);
}
