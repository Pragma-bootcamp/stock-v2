package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public class ArticleUseCase implements IArticleServicePort {
    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }
    @Override
    public ApiResponseFormat<Article> saveArticle(Article article) {
        return articlePersistencePort.saveArticle(article);
    }
}
