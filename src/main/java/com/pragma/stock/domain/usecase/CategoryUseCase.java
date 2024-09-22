package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public class CategoryUseCase  implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public ApiResponseFormat<Category> saveCategory(Category category) {
        if(category.getName()==null || category.getName().isEmpty()){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST, CategoryConstant.CATEGORY_FIELD_NAME_NOT_NULL);
        }
        if(category.getDescription()==null || category.getDescription().isEmpty()){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,CategoryConstant.CATEGORY_FIELD_DESCRIPTION_NOT_NULL);
        }
        if(category.getDescription().length() > CategoryConstant.CATEGORY_DESCRIPTION_MAX_LENGTH ||
                category.getDescription().length() < CategoryConstant.CATEGORY_DESCRIPTION_MIN_LENGTH) {
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,CategoryConstant.CATEGORY_DESCRIPTION_LENGTH_MESSAGE);
        }
        if(category.getName().length() > CategoryConstant.CATEGORY_NAME_MAX_LENGTH ||
                category.getName().length() < CategoryConstant.CATEGORY_NAME_MIN_LENGTH) {
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,CategoryConstant.CATEGORY_NAME_LENGTH_MESSAGE);
        }
        return categoryPersistencePort.saveCategory(category);
    }

    @Override
    public ApiResponseFormat<List<Category>> findAllCategories(int page,int size, String sortDir) {
        return categoryPersistencePort.findAllCategories(page, size, sortDir);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryPersistencePort.findCategoryById(id);
    }

}
