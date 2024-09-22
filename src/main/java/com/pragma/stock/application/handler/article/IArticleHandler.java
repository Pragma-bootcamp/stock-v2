package com.pragma.stock.application.handler.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IArticleHandler {
    ApiResponseFormat<ArticleResponse> saveArticle(ArticleRequest articleRequest);
    ApiResponseFormat<List<ArticleResponse>> listArticles(int page, int size, String sortDir, String sortBy,
                                                          String filterBy, String filterValue);
}
