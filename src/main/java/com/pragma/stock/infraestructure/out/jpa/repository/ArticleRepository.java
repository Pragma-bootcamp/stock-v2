package com.pragma.stock.infraestructure.out.jpa.repository;

import com.pragma.stock.infraestructure.out.jpa.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
    List<ArticleEntity> findByName(String name);
    @Query("SELECT a FROM ArticleEntity a JOIN a.categories c WHERE c.id = :categoryId")
    Page<ArticleEntity> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    @Query("SELECT a FROM ArticleEntity a WHERE a.brand.name = :brandName")
    Page<ArticleEntity> findByBrandName(@Param("brandName") String brandName, Pageable pageable);

}
