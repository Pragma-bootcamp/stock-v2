package com.pragma.stock.application.mapper.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "brand",ignore = true)
    Article toDomain(ArticleRequest articleRequest);
}
