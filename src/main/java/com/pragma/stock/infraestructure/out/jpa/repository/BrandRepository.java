package com.pragma.stock.infraestructure.out.jpa.repository;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    List<BrandEntity> findByName(String name);
}
