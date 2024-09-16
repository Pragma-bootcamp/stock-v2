package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public class CategoryUseCase  implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    @Override
    public ApiResponseFormat<Category> saveCategory(Category category) {
        return categoryPersistencePort.saveCategory(category);
    }

}
