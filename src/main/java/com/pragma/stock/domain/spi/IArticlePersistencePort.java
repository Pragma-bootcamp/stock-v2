package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IArticlePersistencePort {
    ApiResponseFormat<Article> saveArticle(Article article);
    ApiResponseFormat<List<Article>> getArticles(int page,int size,String sortDir, String sortBy,
                                                 String filterBy, String filterValue);
    List<Article> getArticlesById(List<Long> ids);
}
