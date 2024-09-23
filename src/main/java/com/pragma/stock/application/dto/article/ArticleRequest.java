package com.pragma.stock.application.dto.article;

import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.constant.CategoryConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    @NotNull(message = ArticleConstant.ARTICLE_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ArticleConstant.ARTICLE_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = ArticleConstant.ARTICLE_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ArticleConstant.ARTICLE_FIELD_NAME_NOT_EMPTY)
    private String description;
    @NotNull(message = ArticleConstant.ARTICLE_PRICE_NOT_NULL)
    @Positive(message = ArticleConstant.ARTICLE_PRICE_POSITIVE)
    private double price;
    @NotNull(message = ArticleConstant.ARTICLE_QUANTITY_NOT_NULL)
    @Positive(message = ArticleConstant.ARTICLE_QUANTITY_POSITIVE)
    private int quantity;
    @NotNull(message = ArticleConstant.ARTICLE_BRAND_NOT_NULL)
    @Positive(message = ArticleConstant.ARTICLE_BRAND_POSITIVE)
    private Long brand;
    @NotNull(message = ArticleConstant.ARTICLE_CATEGORIES_LENGTH)
    private List<@Positive(message = ArticleConstant.ARTICLE_CATEGORIES_VALUES_TYPE) Long> categories;

}
