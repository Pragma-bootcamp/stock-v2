package com.pragma.stock.application.handler.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.mapper.article.ArticleRequestMapper;
import com.pragma.stock.application.mapper.article.ArticleResponseMapper;
import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements IArticleHandler {
    private final IArticleServicePort iArticleServicePort;
    private final ICategoryServicePort iCategoryServicePort;
    private final IBrandServicePort iBrandServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;

    @Override
    public ApiResponseFormat<ArticleResponse> saveArticle(ArticleRequest articleRequest) {
        validateRequest(articleRequest);
        Article article = articleRequestMapper.toDomain(articleRequest);
        Set<Category> categories = articleRequest
                .getCategories()
                .stream()
                .map(iCategoryServicePort::findCategoryById)
                .collect(Collectors.toSet());
        Brand brand = iBrandServicePort.getBrandById(articleRequest.getBrand());
        article.setBrand(brand);
        article.setCategories(categories);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        ApiResponseFormat<Article> response = iArticleServicePort.saveArticle(article);
        ArticleResponse articleResponse = articleResponseMapper.toDto(response.getData());
        return new ApiResponseFormat<>(articleResponse,null);
    }
    public void validateRequest(ArticleRequest articleRequest) {
        if (new HashSet<Long>(articleRequest.getCategories()).size() != articleRequest.getCategories().size()) {
            throw new ArticleException(HttpStatus.BAD_REQUEST.value(), ArticleConstant.ARTICLE_CATEGORIES_DUPLICATED);
        }
    }
}
