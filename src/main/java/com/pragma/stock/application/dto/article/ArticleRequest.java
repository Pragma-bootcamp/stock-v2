package com.pragma.stock.application.dto.article;

import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
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
    @NotNull(message = ErrorMessages.ARTICLE_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ErrorMessages.ARTICLE_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = ErrorMessages.ARTICLE_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ErrorMessages.ARTICLE_FIELD_NAME_NOT_EMPTY)
    private String description;
    @NotNull(message = ErrorMessages.ARTICLE_PRICE_NOT_NULL)
    @Positive(message = ErrorMessages.ARTICLE_PRICE_POSITIVE)
    private double price;
    @NotNull(message = ErrorMessages.ARTICLE_QUANTITY_NOT_NULL)
    @Positive(message = ErrorMessages.ARTICLE_QUANTITY_POSITIVE)
    private int quantity;
    @NotNull(message = ErrorMessages.ARTICLE_BRAND_NOT_NULL)
    @Positive(message = ErrorMessages.ARTICLE_BRAND_POSITIVE)
    private Long brand;
    @NotNull(message = ErrorMessages.ARTICLE_CATEGORIES_LENGTH)
    private List<@Positive(message = ErrorMessages.ARTICLE_CATEGORIES_VALUES_TYPE) Long> categories;

}
