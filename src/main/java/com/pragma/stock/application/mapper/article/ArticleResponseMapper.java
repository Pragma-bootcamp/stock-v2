package com.pragma.stock.application.mapper.article;

import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.dto.brand.BrandSimpleResponse;
import com.pragma.stock.application.dto.category.CategorySimpleResponse;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ArticleResponseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    ArticleResponse toDto(Article article);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CategorySimpleResponse toSCategoryDto(Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    BrandSimpleResponse toSBrandDto(Brand brand);
}
