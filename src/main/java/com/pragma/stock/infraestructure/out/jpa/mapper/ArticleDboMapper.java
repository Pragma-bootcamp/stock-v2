package com.pragma.stock.infraestructure.out.jpa.mapper;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.infraestructure.out.jpa.entity.ArticleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleDboMapper {

    ArticleEntity toDbo(Article category);

    @InheritInverseConfiguration
    Article toDomain(ArticleEntity entity);
}
