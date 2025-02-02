package com.pragma.stock.application.handler.article;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.dto.brand.BrandSimpleResponse;
import com.pragma.stock.application.dto.category.CategorySimpleResponse;
import com.pragma.stock.application.mapper.article.ArticleRequestMapper;
import com.pragma.stock.application.mapper.article.ArticleResponseMapper;
import com.pragma.stock.domain.utils.UtilConstant;
import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleHandlerTest {
    @Mock
    private  IArticleServicePort iArticleServicePort;
    @Mock
    private  ICategoryServicePort iCategoryServicePort;
    @Mock
    private  IBrandServicePort iBrandServicePort;
    @Mock
    private  ArticleRequestMapper articleRequestMapper;
    @Mock
    private  ArticleResponseMapper articleResponseMapper;
    @InjectMocks
    private ArticleHandler articleHandler;
    private ArticleRequest articleRequest;
    private Article article;
    private ArticleResponse articleResponse;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleRequest = new ArticleRequest();
        article= new Article();
        articleResponse = new ArticleResponse();
    }
    @Test
    void saveArticle() {
        articleRequest.setBrand(1L);
        articleRequest.setCategories(Constant.DEFAULT_LIST_CATEGORIES);
        articleRequest.setBrand(Constant.DEFAULT_BRAND);
        articleRequest.setName(Constant.DEFAULT_NAME);
        articleRequest.setPrice(Constant.DEFAULT_PRICE);
        articleRequest.setQuantity(Constant.DEFAULT_QUANTITY);
        articleRequest.setDescription(Constant.DEFAULT_DESCRIPTION);

        article.setBrand(mock(Brand.class));
        article.setCategories(List.of(mock(Category.class),mock(Category.class)));
        article.setBrand(mock(Brand.class));
        article.setName(Constant.DEFAULT_NAME);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);

        articleResponse.setBrand(mock(BrandSimpleResponse.class));
        articleResponse.setCategories(List.of(mock(CategorySimpleResponse.class),mock(CategorySimpleResponse.class)));
        articleResponse.setName(Constant.DEFAULT_NAME);
        articleResponse.setPrice(Constant.DEFAULT_PRICE);
        articleResponse.setQuantity(Constant.DEFAULT_QUANTITY);
        articleResponse.setDescription(Constant.DEFAULT_DESCRIPTION);

        
        ApiResponseFormat<Article> response = new ApiResponseFormat<>(article,null);
        when(articleRequestMapper.toDomain(articleRequest)).thenReturn(article);
        when(iArticleServicePort.saveArticle(article)).thenReturn(response);
        when(articleResponseMapper.toDto(article)).thenReturn(articleResponse);
        ApiResponseFormat<ArticleResponse> result = articleHandler.saveArticle(articleRequest);
        assertNotNull(result);
        assertNotNull(result.getData());
        assertNull(result.getMetadata());
    }

    @Test
    void listArticles() {
        int page = Constant.PAGE_DEFAULT;
        int size = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        String sortBy = Constant.SORT_BY;
        String filterBy = Constant.FILTER_BY;
        String filterValue = Constant.FILTER_VALUE_STR;
        article = mock(Article.class);
        articleResponse = mock(ArticleResponse.class);
        List<Article> listArticles = List.of(mock(Article.class),mock(Article.class));
        MetadataResponse metadata = new MetadataResponse(Constant.PAGE_DEFAULT,
                Constant.TOTAL_ELEMENTS,
                Constant.TOTAL_PAGES_DEFAULT,
                Constant.PAGE_SIZE);
        ApiResponseFormat<List<Article>> mockResponse = new ApiResponseFormat<>(listArticles,metadata);
        when(iArticleServicePort.getArticles(page,size,sortDir,sortBy,filterBy,filterValue)).thenReturn(mockResponse);
        when(articleResponseMapper.toDto(article)).thenReturn(articleResponse);
        ApiResponseFormat<List<ArticleResponse>> apiResponse = articleHandler
                .listArticles(page,size,sortDir,sortBy,filterBy,filterValue);
        assertNotNull(apiResponse);
        assertNotNull(apiResponse.getMetadata());
    }

    @Test
    void getArticlesPaginationException() {
        int page = Constant.WRONG_PAGE;
        int size = Constant.WRONG_PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        String sortBy = Constant.SORT_BY;
        String filterBy = Constant.FILTER_BY;
        String filterValue = Constant.FILTER_VALUE_STR;
        PaginationException exception = assertThrows(PaginationException.class,()->{
            articleHandler.listArticles(page,size,sortDir,sortBy,filterBy,filterValue);
        });
        assertEquals(UtilConstant.PAGINATION_NEGATIVE,exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());

    }
}