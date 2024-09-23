package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.CategoryException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.utils.Constant;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryUseCaseTest {
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
        Category category = new Category();
        category.setDescription(Constant.DEFAULT_DESCRIPTION);
        category.setName(Constant.DEFAULT_NAME);
        when(iCategoryPersistencePort.saveCategory(category)).thenReturn(new ApiResponseFormat<>(category, null));
        ApiResponseFormat<Category> created = categoryUseCase.saveCategory(category);
        assertNotNull(created);
    }


    @Test
    void saveBrandExceptionShortName() {
        Category category = new Category();
        category.setDescription(Constant.DEFAULT_DESCRIPTION);
        category.setName(Constant.DEFAULT_NAME.substring(0, 1));
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_NAME_LENGTH_MESSAGE, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionEmptyName() {
        Category category = new Category();
        category.setDescription(Constant.DEFAULT_DESCRIPTION);
        category.setName("");
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_FIELD_NAME_NOT_EMPTY, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionNullName() {
        Category category = new Category();
        category.setDescription(Constant.DEFAULT_DESCRIPTION);
        category.setName(null);
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_FIELD_NAME_NOT_NULL, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionShortDescription() {
        Category category = new Category();
        category.setDescription(Constant.DEFAULT_DESCRIPTION.substring(0, 1));
        category.setName(Constant.DEFAULT_NAME);
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_DESCRIPTION_LENGTH_MESSAGE, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionEmptyDescription() {
        Category category = new Category();
        category.setDescription("");
        category.setName(Constant.DEFAULT_NAME);
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionNullDescription() {
        Category category = new Category();
        category.setDescription(null);
        category.setName(Constant.DEFAULT_NAME);
        CategoryException exception = assertThrows(CategoryException.class, () -> categoryUseCase.saveCategory(category));
        assertEquals(ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_NULL, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void listCategories() {
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        when(iCategoryPersistencePort.findAllCategories(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_ASC))
                .thenReturn(new ApiResponseFormat<>(categories, null));
        ApiResponseFormat<List<Category>> listed = categoryUseCase
                .findAllCategories(Constant.PAGE_DEFAULT, Constant.PAGE_SIZE, Constant.ORDER_ASC);
        assertNotNull(listed);
    }
}