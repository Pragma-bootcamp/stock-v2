package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.ArticleDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;

@RequiredArgsConstructor

public class ArticleJpaAdapter implements IArticlePersistencePort {
    private final ArticleRepository articleRepository;
    private final ArticleDboMapper articleDboMapper;

    @Override
    public ApiResponseFormat<Article> saveArticle(Article article) {
        List<ArticleEntity> existArticle = articleRepository.findByName(article.getName());
        if(!existArticle.isEmpty()) {
            throw new ArticleException(HttpStatus.CONFLICT.value(),
                    String.format(ArticleConstant.ARTICLE_ALREADY_EXIST,article.getName()));
        }
        ArticleEntity toCreate = articleDboMapper.toDbo(article);
        ArticleEntity createdArticle = articleRepository.save(toCreate);
        return new ApiResponseFormat<>(articleDboMapper.toDomain(createdArticle),null);
    }
}
