package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.mapper.CategoryRequestMapper;
import com.pragma.stock.application.mapper.CategoryResponseMapper;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryHandlerTest {
    @Mock
    private ICategoryHandler iCategoryHandler;
    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private CategoryRequestMapper categoryRequestMapper;
    @Mock
    private CategoryResponseMapper categoryResponseMapper;
    @InjectMocks
    private CategoryHandler categoryHandler;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveCategory() {
        CategoryRequest categoryRequest = mock(CategoryRequest.class);
        Category category = mock(Category.class);
        CategoryResponse categoryResponse = mock(CategoryResponse.class);
        ApiResponseFormat<Category> apiResponseFormat = new ApiResponseFormat<Category>();
        when(categoryRequestMapper.toDomain(categoryRequest)).thenReturn(category);
        when(categoryServicePort.saveCategory(category)).thenReturn(apiResponseFormat);
        when(categoryResponseMapper.toDto(category)).thenReturn(categoryResponse);
        ApiResponseFormat<CategoryResponse> apiResponseFormatResponse = categoryHandler.saveCategory(categoryRequest);
        assertNotNull(apiResponseFormatResponse);
    }
}