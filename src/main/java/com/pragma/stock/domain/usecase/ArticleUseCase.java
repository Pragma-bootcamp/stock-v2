package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.constant.*;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import java.util.List;


public class ArticleUseCase implements IArticleServicePort {
    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort,
                          ICategoryPersistencePort categoryPersistencePort,
                          IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public ApiResponseFormat<Article> saveArticle(Article article) {
        validateArticle(article);
        List<Category> categories = article.getCategories().stream()
                .map((category -> categoryPersistencePort.findCategoryById(category.getId()))).toList();
        Brand brand = brandPersistencePort.findBrandById(article.getBrand().getId());
        article.setBrand(brand);
        article.setCategories(categories);
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public ApiResponseFormat<List<Article>> getArticles(int page, int size, String sortDir,
                                                        String sortBy, String filterBy, String filterValue) {
        validateGetArticlesParams(sortDir, sortBy, filterBy, filterValue);
        return articlePersistencePort.getArticles(page, size, sortDir, sortBy, filterBy, filterValue);
    }
    public void validateArticle (Article article){
        if (article.getBrand() == null) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_BRAND_NOT_NULL);
        }
        if (article.getCategories() == null) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_CATEGORIES_NOT_NULL);
        }
        if (article.getCategories().isEmpty() ||
                article.getCategories().size() > ArticleConstant.ARTICLE_CATEGORIES_MAX_LENGTH) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_CATEGORIES_LENGTH);
        }
        if (article.getName() == null) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_FIELD_NAME_NOT_NULL);
        }
        if (article.getName().isEmpty()) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_FIELD_NAME_NOT_EMPTY);
        }
        if (article.getDescription() == null) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_FIELD_DESCRIPTION_NOT_NULL);
        }
        if (article.getDescription().isEmpty()) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_FIELD_DESCRIPTION_NOT_EMPTY);
        }
        if (article.getDescription().length() > ArticleConstant.ARTICLE_DESCRIPTION_MAX_LENGTH ||
                article.getDescription().length() < ArticleConstant.ARTICLE_DESCRIPTION_MIN_LENGTH) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_DESCRIPTION_LENGTH_MESSAGE);
        }
        if (article.getName().length() > ArticleConstant.ARTICLE_NAME_MAX_LENGTH ||
                article.getName().length() < ArticleConstant.ARTICLE_NAME_MIN_LENGTH) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_NAME_LENGTH_MESSAGE);
        }
        if (article.getCategories().size() != article.getCategories().stream().map(Category::getId).distinct().count()) {
            throw new ArticleException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.ARTICLE_CATEGORIES_DUPLICATED);
        }
    }
    public void validateGetArticlesParams(String sortDir, String sortBy, String filterBy, String filterValue) {
        if (!(sortDir.equalsIgnoreCase(Element.ASC.name().toLowerCase()) ||
                sortDir.equalsIgnoreCase(Element.DESC.name().toLowerCase())))
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.SORT_DIR_ERROR);
        if (sortBy != null && !sortBy.equals(Element.NAME.name().toLowerCase()))
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.SORT_ARTICLE_BY_ERROR);
        if (filterBy != null && !filterBy.isEmpty()) {
            if (filterValue == null) throw new PaginationException(ErrorCodeConstant.BAD_REQUEST,
                    ErrorMessages.FILTER_VALUE_NOT_BE_NULL);
            if (!List.of(Element.NAME.name().toLowerCase(),Element.CATEGORIES.name().toLowerCase(),
                    Element.BRAND.name().toLowerCase()).contains(filterBy)) {
                throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.FILTER_ARTICLE_BY_ERROR);
            }
            if (filterBy.equals(Element.CATEGORIES.name().toLowerCase()) && !filterValue.matches("\\d+")) {
                throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.CATEGORIES_MUST_BE_A_NUMBER);
            }
        }
    }
}
