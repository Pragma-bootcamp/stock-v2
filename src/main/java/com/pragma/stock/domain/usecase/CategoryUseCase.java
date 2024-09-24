package com.pragma.stock.domain.usecase;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.CategoryException;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoryUseCase  implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public ApiResponseFormat<Category> saveCategory(Category category) {
        validateCategory(category);
        return categoryPersistencePort.saveCategory(category);
    }

    @Override
    public ApiResponseFormat<List<Category>> findAllCategories(int page,int size, String sortDir, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals(Element.NAME.name().toLowerCase())) {
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.CATEGORY_SORT_BY_NOT_ALLOW);
        }
        return categoryPersistencePort.findAllCategories(page, size, sortDir,sortBy);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryPersistencePort.findCategoryById(id);
    }

    public void validateCategory(Category category){
        if(category.getName()==null){
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.CATEGORY_FIELD_NAME_NOT_NULL);
        }
        if(category.getName().isEmpty()){
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.CATEGORY_FIELD_NAME_NOT_EMPTY);
        }
        if(category.getDescription()==null){
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_NULL);
        }
        if(category.getDescription().isEmpty()){
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY);
        }
        if(category.getDescription().length() > CategoryConstant.CATEGORY_DESCRIPTION_MAX_LENGTH ||
                category.getDescription().length() < CategoryConstant.CATEGORY_DESCRIPTION_MIN_LENGTH) {
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.CATEGORY_DESCRIPTION_LENGTH_MESSAGE);
        }
        if(category.getName().length() > CategoryConstant.CATEGORY_NAME_MAX_LENGTH ||
                category.getName().length() < CategoryConstant.CATEGORY_NAME_MIN_LENGTH) {
            throw new CategoryException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.CATEGORY_NAME_LENGTH_MESSAGE);
        }
    }
}
