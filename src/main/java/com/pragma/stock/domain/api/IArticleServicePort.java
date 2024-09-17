package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IArticleServicePort {
    ApiResponseFormat<Article> saveArticle(Article article);
}
