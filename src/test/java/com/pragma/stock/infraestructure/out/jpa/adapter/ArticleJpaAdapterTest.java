package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.pragma.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.ArticleDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.ArticleRepository;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class ArticleJpaAdapterTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleDboMapper articleDboMapper;
    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;
    private Article article;
    private ArticleEntity articleEntity;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        article = new Article();
        articleEntity = new ArticleEntity();

        articleEntity.setId(Constant.DEFAULT_ID);
        articleEntity.setName(Constant.DEFAULT_NAME);
        articleEntity.setDescription(Constant.DEFAULT_DESCRIPTION);
        articleEntity.setPrice(Constant.DEFAULT_PRICE);
        articleEntity.setQuantity(Constant.DEFAULT_QUANTITY);
        articleEntity.setCreatedAt(LocalDateTime.now());
        articleEntity.setUpdatedAt(LocalDateTime.now());
        articleEntity.setBrand(mock(BrandEntity.class));
        articleEntity.setCategories(new HashSet<>(List.of(mock(CategoryEntity.class),mock(CategoryEntity.class))));

        article.setId(Constant.DEFAULT_ID);
        article.setName(Constant.DEFAULT_NAME);
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        article.setBrand(mock(Brand.class));
        article.setCategories(List.of(mock(Category.class),mock(Category.class)));
    }

    @Test
    void saveArticleAlreadyExist() {
        when(articleRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(List.of(articleEntity));
        ArticleException exception = assertThrows(ArticleException.class,()->{
            articleJpaAdapter.saveArticle(article);
        });
        assertEquals(String.format(ErrorMessages.ARTICLE_ALREADY_EXIST,article.getName()), exception.getErrorMessage());
        assertEquals(HttpStatus.CONFLICT.value(),exception.getErrorCode());
    }
    @Test
    void saveArticle() {
        when(articleRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(List.of());
        when(articleDboMapper.toDbo(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(articleEntity);
        when (articleDboMapper.toDomain(articleEntity)).thenReturn(article);
        ApiResponseFormat<Article> result = articleJpaAdapter.saveArticle(article);
        assertNotNull(result);
        assertNotNull(result.getData());
        assertNull(result.getMetadata());

    }

    @Test
    void getArticles() {
        int page = Constant.PAGE_DEFAULT;
        int pageSize = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        String sortBy = Constant.SORT_BY;
        String filterBy = Constant.FILTER_BY;
        String filterValue = Constant.FILTER_VALUE_STR;
        String brandName = Constant.DEFAULT_NAME;
        Long categoryId = Constant.DEFAULT_CATEGORY_ID;
        ArticleEntity articleEntity1 = mock(ArticleEntity.class);
        ArticleEntity articleEntity2 = mock(ArticleEntity.class);
        List<ArticleEntity> articleEntityList = List.of(articleEntity1,articleEntity2);
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, sortBy));
        Page<ArticleEntity> articleEntityPage = new PageImpl<>(articleEntityList);
        article = mock(Article.class);
        articleEntity = mock(ArticleEntity.class);
        when(articleRepository.findByBrandName(brandName,pageable)).thenReturn(articleEntityPage);
        when(articleRepository.findByCategoryId(categoryId,pageable)).thenReturn(articleEntityPage);
        when(articleRepository.findAll(pageable)).thenReturn(articleEntityPage);
        when(articleDboMapper.toDbo(article)).thenReturn(articleEntity);
        ApiResponseFormat<List<Article>> articles = articleJpaAdapter
                .getArticles(page,pageSize,sortDir,sortBy,filterBy,filterValue);
        assertNotNull(articles);
        assertEquals(Constant.PAGE_SIZE, articles.getData().size());

        verify(articleRepository).findAll(argThat((Pageable p) ->
                p.getPageNumber() == page &&
                        p.getPageSize() == pageSize &&
                        Objects.requireNonNull(p.getSort().getOrderFor(Element.NAME.name().toLowerCase())).getDirection()== Sort.Direction.ASC
        ));

        MetadataResponse metadata = articles.getMetadata();
        assertNotNull(metadata);
        assertEquals(page, metadata.getCurrentPage());
        assertEquals(Constant.TOTAL_ELEMENTS, metadata.getTotalItems());
        assertEquals(Constant.TOTAL_PAGES_DEFAULT, metadata.getTotalPages());
        assertEquals(pageSize, metadata.getPageSize());

        ApiResponseFormat<List<Article>> articles2 = articleJpaAdapter
                .getArticles(page,pageSize,sortDir,sortBy,Element.CATEGORIES.name().toLowerCase(),
                        categoryId.toString());
        assertNotNull(articles2);
        assertEquals(Constant.PAGE_SIZE, articles2.getData().size());

        ApiResponseFormat<List<Article>> articles3 = articleJpaAdapter
                .getArticles(page,pageSize,sortDir,sortBy,Element.BRAND.name().toLowerCase(),
                        filterValue);
        assertNotNull(articles3);
        assertEquals(Constant.PAGE_SIZE, articles3.getData().size());


    }

    @Test
    void getArticlesById() {
        List<Long> articleIds = Constant.DEFAULT_LIST_CATEGORIES;
        ArticleEntity articleEntity1 = mock(ArticleEntity.class);
        ArticleEntity articleEntity2 = mock(ArticleEntity.class);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);

        when(articleRepository.findById(articleIds.getFirst())).thenReturn(Optional.of(articleEntity1));
        when(articleRepository.findById(articleIds.getLast())).thenReturn(Optional.of(articleEntity2));
        when(articleDboMapper.toDomain(articleEntity1)).thenReturn(article1);
        when(articleDboMapper.toDomain(articleEntity2)).thenReturn(article2);

        // Act
        List<Article> articles = articleJpaAdapter.getArticlesById(articleIds);

        // Assert
        assertNotNull(articles);
        assertEquals(articleIds.size(), articles.size());
        assertEquals(article1, articles.get(0));
        assertEquals(article2, articles.get(1));

    }

    @Test
    void getArticlesByIdThrowException() {
        List<Long> articleIds = Constant.DEFAULT_LIST_CATEGORIES;
        when(articleRepository.findById(articleIds.getFirst())).thenReturn(Optional.empty());
        // Act
        ArticleException exception = assertThrows(ArticleException.class,()->{
            articleJpaAdapter.getArticlesById(articleIds);
        });
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());

        assertEquals(ErrorMessages.ARTICLE_NOT_FOUND, exception.getErrorMessage());


    }
}