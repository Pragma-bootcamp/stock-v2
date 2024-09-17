package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        ApiResponseFormat<Article> response = new ApiResponseFormat<>(article,null);
        when(iArticlePersistencePort.saveArticle(article)).thenReturn(response);

        ApiResponseFormat<Article> result = articleUseCase.saveArticle(article);
        assertNotNull(result);
        assertNotNull(result.getData());
        assertNull(result.getMetadata());
        assertEquals(result.getData().getName(), article.getName());
        assertEquals(result.getData().getDescription(), article.getDescription());
        assertEquals(result.getData().getPrice(), article.getPrice());


    }
}