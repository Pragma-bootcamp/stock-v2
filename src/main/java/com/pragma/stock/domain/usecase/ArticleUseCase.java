package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

public class ArticleUseCase implements IArticleServicePort {
    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }

    @Override
    public ApiResponseFormat<Article> saveArticle(Article article) {
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public ApiResponseFormat<List<Article>> getArticles(int page, int size, String sortDir,
                                                        String sortBy, String filterBy, String filterValue) {
        validateGetArticlesParams(sortDir,sortBy, filterBy, filterValue);
        return articlePersistencePort.getArticles(page, size, sortDir, sortBy, filterBy, filterValue);
    }
    public void validateGetArticlesParams(String sortDir,String sortBy, String filterBy, String filterValue) {
        if(!(sortDir.equalsIgnoreCase(Element.ASC.name().toLowerCase()) ||
                sortDir.equalsIgnoreCase(Element.DESC.name().toLowerCase())))
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.SORT_DIR_ERROR);
        if(sortBy!= null && !sortBy.equals(Element.NAME.name().toLowerCase()))
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.SORT_ARTICLE_BY_ERROR);
        if(filterBy != null && !filterBy.isEmpty()){
            if(filterValue == null) throw new PaginationException(ErrorCodeConstant.BAD_REQUEST,
                    ErrorMessages.FILTER_VALUE_NOT_BE_NULL);
            if (!List.of(Element.NAME.name().toLowerCase(),
                    Element.CATEGORIES.name().toLowerCase(),
                    Element.BRAND.name().toLowerCase()).contains(filterBy)) {
                throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.FILTER_ARTICLE_BY_ERROR);
            }
            if (filterBy.equals(Element.CATEGORIES.name().toLowerCase()) && !filterValue.matches("\\d+")) {
                throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.CATEGORIES_MUST_BE_A_NUMBER);
            }
        }
    }
}
