package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.ArticleConstant;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
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
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleJpaAdapterTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleDboMapper articleDboMapper;
    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;
    private Article article;
    private Brand brand;
    private Category category;
    private ArticleEntity articleEntity;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        brand = new Brand();
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
        article.setCategories(new HashSet<>(List.of(mock(Category.class),mock(Category.class))));
    }

    @Test
    void saveArticleAlreadyExist() {
        when(articleRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(List.of(articleEntity));
        ArticleException exception = assertThrows(ArticleException.class,()->{
            articleJpaAdapter.saveArticle(article);
        });
        assertEquals(String.format(ArticleConstant.ARTICLE_ALREADY_EXIST,article.getName()), exception.getErrorMessage());
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
}