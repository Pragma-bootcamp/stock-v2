package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryUseCaseTest {
    @Mock
    private ICategoryServicePort iCategoryServicePort;
    @Mock
    private ICategoryPersistencePort iCategoryPersistencePort;
    @InjectMocks
    private CategoryUseCase categoryUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveCategory() {
        Category category = mock(Category.class);
        Category newCategory = new Category();
        when(iCategoryPersistencePort.saveCategory(category)).thenReturn(new ApiResponseFormat<>(newCategory,null));
        ApiResponseFormat<Category> created =categoryUseCase.saveCategory(category);
        assertNotNull(created);
    }
}