package com.pragma.stock.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.dto.brand.BrandSimpleResponse;
import com.pragma.stock.application.dto.category.CategorySimpleResponse;
import com.pragma.stock.application.handler.article.IArticleHandler;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import com.pragma.stock.utils.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IArticleHandler iArticleHandler;
    ObjectMapper objectMapper = new ObjectMapper();
    private ArticleRequest articleRequest;
    private Article article;
    private ArticleResponse articleResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleRequest = new ArticleRequest();
        article = new Article();
        articleResponse = new ArticleResponse();
    }

    @Test
    void createArticle() throws Exception {
        articleRequest.setName(Constant.DEFAULT_NAME);
        articleRequest.setPrice(Constant.DEFAULT_PRICE);
        articleRequest.setQuantity(Constant.DEFAULT_QUANTITY);
        articleRequest.setBrand(Constant.DEFAULT_BRAND);
        articleRequest.setDescription(Constant.DEFAULT_DESCRIPTION);
        articleRequest.setCategories(Constant.DEFAULT_LIST_CATEGORIES);

        articleResponse.setName(Constant.DEFAULT_NAME);
        articleResponse.setPrice(Constant.DEFAULT_PRICE);
        articleResponse.setQuantity(Constant.DEFAULT_QUANTITY);
        articleResponse.setBrand(mock(BrandSimpleResponse.class));
        articleResponse.setDescription(Constant.DEFAULT_DESCRIPTION);
        articleResponse.setCategories(List.of(mock(CategorySimpleResponse.class)));
        articleResponse.setCreatedAt(LocalDateTime.now());
        articleResponse.setUpdatedAt(LocalDateTime.now());

        article.setName(Constant.DEFAULT_NAME);
        article.setPrice(Constant.DEFAULT_PRICE);
        article.setQuantity(Constant.DEFAULT_QUANTITY);
        article.setBrand(mock(Brand.class));
        article.setDescription(Constant.DEFAULT_DESCRIPTION);
        article.setCategories(List.of(mock(Category.class)));
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        ApiResponseFormat<ArticleResponse> response = new ApiResponseFormat<>(articleResponse, null);
        given(iArticleHandler.saveArticle(ArgumentMatchers.any())).willReturn(response);

        ResultActions resultActions = mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.metadata").doesNotExist());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(Constant.DEFAULT_NAME));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.description")
                .value(Constant.DEFAULT_DESCRIPTION));
    }

    @Test
    void getArticles() throws Exception {
        Integer page = Constant.PAGE_DEFAULT;
        Integer pageSize = Constant.PAGE_SIZE;
        List<ArticleResponse> articleResponseList = List.of(mock(ArticleResponse.class), mock(ArticleResponse.class));
        MetadataResponse metadata = new MetadataResponse(Constant.PAGE_DEFAULT,
                Constant.TOTAL_ELEMENTS, Constant.TOTAL_PAGES_DEFAULT, Constant.PAGE_SIZE);
        ApiResponseFormat<List<ArticleResponse>> responseList = new ApiResponseFormat<>(articleResponseList, metadata);

        given(iArticleHandler.listArticles(page, pageSize, null, null, null, null))
                .willReturn(responseList);
        ResultActions response = mockMvc.perform(get("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam(Element.SIZE.name().toLowerCase(), pageSize.toString())
                .queryParam(Element.PAGE.name().toLowerCase(), page.toString())
        );
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}