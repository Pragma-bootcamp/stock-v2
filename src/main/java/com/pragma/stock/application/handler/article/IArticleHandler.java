package com.pragma.stock.application.handler.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IArticleHandler {
    ApiResponseFormat<ArticleResponse> saveArticle(ArticleRequest articleRequest);
}
