package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import com.pragma.stock.utils.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleUseCaseTest {
    @Mock
    private IArticleServicePort iArticleServicePort;
    @Mock
    private IArticlePersistencePort iArticlePersistencePort;
    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveArticle() {
        Article article = new Article();
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setName(Constant.DEFAULT_NAME);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        article.setBrand(mock(Brand.class));
        article.setCategories(new HashSet<>(List.of(mock(Category.class), mock(Category.class))));
        ApiResponseFormat<Article> response = new ApiResponseFormat<>(article, null);
        when(iArticlePersistencePort.saveArticle(article)).thenReturn(response);

        ApiResponseFormat<Article> result = articleUseCase.saveArticle(article);
        assertNotNull(result);
        assertNotNull(result.getData());
        assertNull(result.getMetadata());
        assertEquals(result.getData().getName(), article.getName());
        assertEquals(result.getData().getDescription(), article.getDescription());
        assertEquals(result.getData().getPrice(), article.getPrice());


    }

    @Test
    void getArticles() {
        List<Article> articles = List.of(mock(Article.class), mock(Article.class));
        MetadataResponse metadata = new MetadataResponse(Constant.PAGE_DEFAULT,
                Constant.TOTAL_ELEMENTS,
                Constant.TOTAL_PAGES_DEFAULT,
                Constant.PAGE_SIZE);
        when(iArticlePersistencePort.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_ASC,
                Constant.SORT_BY, Constant.FILTER_BY, Constant.FILTER_VALUE_STR))
                .thenReturn(new ApiResponseFormat<>(articles,metadata));
        ApiResponseFormat<List<Article>> lister = articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_ASC,
                Constant.SORT_BY, Constant.FILTER_BY, Constant.FILTER_VALUE_STR);
        assertNotNull(lister);
        assertNotNull(lister.getData());
        assertNotNull(lister.getMetadata());
        assertEquals(lister.getData().size(), articles.size());
    }
    @Test
    void getArticlesExceptionBadSortDir(){

        PaginationException exception = assertThrows(PaginationException.class,()->{
           articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.WRONG_PAGE_SORT_DIR,
                   Constant.SORT_BY, Constant.FILTER_BY, Constant.FILTER_VALUE_STR);
        });

        assertEquals(ErrorMessages.SORT_DIR_ERROR, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }

    @Test
    void getArticlesExceptionBadSortBy(){

        PaginationException exception = assertThrows(PaginationException.class,()->{
            articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_DESC,
                    Constant.WRONG_SORT_BY, Constant.FILTER_BY, Constant.FILTER_VALUE_STR);
        });

        assertEquals(ErrorMessages.SORT_ARTICLE_BY_ERROR, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }

    @Test
    void getArticlesExceptionBadFilterBy(){

        PaginationException exception = assertThrows(PaginationException.class,()->{
            articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_DESC,
                    Constant.SORT_BY, Constant.WRONG_FILTER_BY, Constant.FILTER_VALUE_STR);
        });
        assertEquals(ErrorMessages.FILTER_ARTICLE_BY_ERROR, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }

    @Test
    void getArticlesExceptionBadFilterValue(){
        PaginationException exception = assertThrows(PaginationException.class,()->{
            articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_DESC,
                    Constant.SORT_BY, Constant.FILTER_BY, null);
        });
        assertEquals(ErrorMessages.FILTER_VALUE_NOT_BE_NULL, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }
    @Test
    void getArticlesExceptionBadFilterValueInt(){
        String categoriesField =Element.CATEGORIES.name().toLowerCase();
        PaginationException exception = assertThrows(PaginationException.class,()->{
            articleUseCase.getArticles(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_DESC,
                    Constant.SORT_BY, categoriesField, Constant.FILTER_VALUE_STR);
        });
        assertEquals(ErrorMessages.CATEGORIES_MUST_BE_A_NUMBER, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }
}