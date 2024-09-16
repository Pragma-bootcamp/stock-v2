package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface ICategoryPersistencePort {
    ApiResponseFormat<Category> saveCategory(Category category);
    ApiResponseFormat<List<Category>> findAllCategories(int page,int size, String sortDir);
}
