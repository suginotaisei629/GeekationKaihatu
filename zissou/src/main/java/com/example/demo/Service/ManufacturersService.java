package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ManufacturersRepository;
import com.example.demo.entity.manufacturers;

@Service
public class ManufacturersService {

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    // すべてのメーカー情報を取得（作成日時と更新日時を除外）
    public List<manufacturers> getAllManufacturers() {
        return manufacturersRepository.findAll();
    }
    
    public manufacturers saveManufacturer(manufacturers manufacturer) {
        return manufacturersRepository.save(manufacturer);
    }
    
    public manufacturers findById(Long id) {
        Optional<manufacturers> manufacturer = manufacturersRepository.findById(id);
        return manufacturer.orElse(null);  // メーカーが見つからない場合はnullを返す
    }

    // メーカーの削除
    public void deleteManufacturer(Long id) {
        manufacturersRepository.deleteById(id);
    }
    
    public manufacturers getManufacturerById(Long id) {
        Optional<manufacturers> manufacturerOpt = manufacturersRepository.findById(id);
        return manufacturerOpt.orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }
}
