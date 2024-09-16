package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface ICategoryPersistencePort {
    ApiResponseFormat<Category> saveCategory(Category category);
}
