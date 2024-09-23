package com.pragma.stock.application.dto.article;

import com.pragma.stock.application.dto.brand.BrandSimpleResponse;
import com.pragma.stock.application.dto.category.CategorySimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private Long id;
    private String name;
    private String description;
    private BrandSimpleResponse brand;
    private List<CategorySimpleResponse> categories;
    private double price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
