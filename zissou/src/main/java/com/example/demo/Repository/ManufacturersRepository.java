package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.manufacturers;

public interface ManufacturersRepository extends JpaRepository<manufacturers, Long> {
    // findAll() メソッドは JpaRepository に既に含まれているため、追加のメソッドは不要
}
