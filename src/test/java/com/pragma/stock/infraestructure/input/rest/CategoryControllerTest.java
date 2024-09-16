package com.pragma.stock.infraestructure.input.rest;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.handler.category.ICategoryHandler;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ICategoryHandler iCategoryHandler;
    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory() throws Exception {
        categoryRequest = new CategoryRequest("Testing category","Testing Description");
        CategoryResponse category= mock(CategoryResponse.class);
        ApiResponseFormat<CategoryResponse> mockResponse = new ApiResponseFormat<>(category,null);
        given(iCategoryHandler.saveCategory(ArgumentMatchers.any())).willReturn(mockResponse);
        ResultActions response = mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryRequest)));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.metadata").doesNotExist());
    }
}