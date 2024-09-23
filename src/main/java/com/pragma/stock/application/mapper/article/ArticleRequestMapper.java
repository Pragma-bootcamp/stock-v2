package com.pragma.stock.application.mapper.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.mapper.category.CategoryRequestMapper;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {CategoryRequestMapper.class})
public interface ArticleRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoryIdsToCategories")
    @Mapping(source = "brand", target = "brand.id")
    Article toDomain(ArticleRequest articleRequest);

}
