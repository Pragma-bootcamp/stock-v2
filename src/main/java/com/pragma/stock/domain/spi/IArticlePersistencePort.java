package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IArticlePersistencePort {
    ApiResponseFormat<Article> saveArticle(Article article);
}
