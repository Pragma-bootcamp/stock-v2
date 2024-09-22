package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IArticleServicePort {
    ApiResponseFormat<Article> saveArticle(Article article);
    ApiResponseFormat<List<Article>> getArticles(int page, int size, String sortDir, String sortBy,
                                                 String filterBy, String filterValue);
}
