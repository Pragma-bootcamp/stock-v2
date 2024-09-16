package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.exception.PaginationException;
import com.pragma.stock.application.mapper.CategoryRequestMapper;
import com.pragma.stock.application.mapper.CategoryResponseMapper;
import com.pragma.stock.application.utils.UtilConstant;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.utils.Constant;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
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

    @Test
    void getAllCategories() {
        int page = Constant.PAGE_DEFAULT;
        int size = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        Category category = mock(Category.class);
        CategoryResponse categoryResponse = mock(CategoryResponse.class);
        List<Category> listCategory = List.of(mock(Category.class),mock(Category.class));
        MetadataResponse metadata = mock(MetadataResponse.class);
        ApiResponseFormat<List<Category>> mockResponse = new ApiResponseFormat<>(listCategory,metadata);
        when(categoryServicePort.findAllCategories(page,size,sortDir)).thenReturn(mockResponse);
        when(categoryResponseMapper.toDto(category)).thenReturn(categoryResponse);
        ApiResponseFormat<List<CategoryResponse>> apiResponse = categoryHandler.findAllCategories(page,size,sortDir);
        assertNotNull(apiResponse);
    }
    @Test
    void getAllCategoriesBadPageSize(){
        int page = Constant.WRONG_PAGE;
        int size = Constant.WRONG_PAGE_SIZE;
        String sortDir = Constant.WRONG_PAGE_SORT_DIR;
        PaginationException exception = assertThrows(PaginationException.class, ()->{
            categoryHandler.findAllCategories(page,size,sortDir);
        });
        assertEquals(UtilConstant.PAGINATION_NEGATIVE, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @Test
    void getAllCategoriesBadSortDirSize(){
        int page = Constant.PAGE_DEFAULT;
        int size = Constant.PAGE_SIZE;
        String sortDir = Constant.WRONG_PAGE_SORT_DIR;
        PaginationException exception = assertThrows(PaginationException.class, ()->{
            categoryHandler.findAllCategories(page,size,sortDir);
        });
        assertEquals(UtilConstant.ORDER_DIR_VALID_OPTIONS, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }
}