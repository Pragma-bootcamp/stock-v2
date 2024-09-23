package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
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
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleUseCaseTest {
    @Mock
    private IArticleServicePort iArticleServicePort;
    @Mock
    private IArticlePersistencePort iArticlePersistencePort;
    @Mock
    private ICategoryPersistencePort iCategoryPersistencePort;
    @Mock
    private IBrandPersistencePort iBrandPersistencePort;
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
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(1L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category2 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1,category2));
        ApiResponseFormat<Article> response = new ApiResponseFormat<>(article, null);
        when(iArticlePersistencePort.saveArticle(article)).thenReturn(response);
        when(iCategoryPersistencePort.findCategoryById(anyLong())).thenReturn(category1);
        when(iBrandPersistencePort.findBrandById(anyLong())).thenReturn(brand);
        ApiResponseFormat<Article> result = articleUseCase.saveArticle(article);
        assertNotNull(result);
        assertNotNull(result.getData());
        assertNull(result.getMetadata());
        assertEquals(result.getData().getName(), article.getName());
        assertEquals(result.getData().getDescription(), article.getDescription());
        assertEquals(result.getData().getPrice(), article.getPrice());
    }

    @Test
    void saveArticleExceptionsBrandNull() {
        Article article = new Article();
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setName(Constant.DEFAULT_NAME);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(null);
        Category category1 = new Category(1L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category2 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1,category2));
        ApiResponseFormat<Article> response = new ApiResponseFormat<>(article, null);
        when(iArticlePersistencePort.saveArticle(article)).thenReturn(response);
        when(iCategoryPersistencePort.findCategoryById(anyLong())).thenReturn(category1);
        when(iBrandPersistencePort.findBrandById(anyLong())).thenReturn(brand);
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_BRAND_NOT_NULL,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsCategoriesNull() {
        Article article = new Article();
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setName(Constant.DEFAULT_NAME);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_CATEGORIES_NOT_NULL,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsCategoriesMoreThanThree() {
        Article article = new Article();
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setName(Constant.DEFAULT_NAME);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category2 = new Category(1L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category3 = new Category(2L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category4 = new Category(3L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1,category2,category3,category4));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_CATEGORIES_LENGTH,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsNameEmpty() {
        Article article = new Article();
        article.setName("");
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category2 = new Category(1L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category3 = new Category(2L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);

        article.setCategories(List.of(category1,category2,category3));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_FIELD_NAME_NOT_EMPTY,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsNameNull() {
        Article article = new Article();
        article.setName(null);
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_FIELD_NAME_NOT_NULL,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsNameVeryShort() {
        Article article = new Article();
        article.setName(Constant.DEFAULT_NAME.substring(0,1));
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_NAME_LENGTH_MESSAGE,exception.getErrorMessage());
    }

    @Test
    void saveArticleExceptionsDescriptionEmpty() {
        Article article = new Article();
        article.setName(Constant.DEFAULT_NAME);
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription("");
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_FIELD_DESCRIPTION_NOT_EMPTY,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsDescriptionNull() {
        Article article = new Article();
        article.setName(Constant.DEFAULT_NAME);
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(null);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_FIELD_DESCRIPTION_NOT_NULL,exception.getErrorMessage());
    }

    @Test
    void saveArticleExceptionsDuplicateCategories() {
        Article article = new Article();
        article.setName(Constant.DEFAULT_NAME);
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        Category category2 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1,category2));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_CATEGORIES_DUPLICATED,exception.getErrorMessage());
    }
    @Test
    void saveArticleExceptionsDescriptionVeryShort() {
        Article article = new Article();
        article.setName(Constant.DEFAULT_NAME);
        article.setUpdatedAt(LocalDateTime.now());
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription(Constant.DEFAULT_DESCRIPTION.substring(0,1));
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        Brand brand = new Brand(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setBrand(brand);
        Category category1 = new Category(0L,Constant.DEFAULT_NAME,Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(category1));
        ArticleException exception = assertThrows(ArticleException.class, ()->{
            articleUseCase.saveArticle(article);
        });
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
        assertEquals(ErrorMessages.ARTICLE_DESCRIPTION_LENGTH_MESSAGE,exception.getErrorMessage());
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