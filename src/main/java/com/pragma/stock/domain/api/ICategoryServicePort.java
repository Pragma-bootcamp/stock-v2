package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface ICategoryServicePort {
    ApiResponseFormat<Category> saveCategory(Category category);
    ApiResponseFormat<List<Category>> findAllCategories(int page, int size, String sortDir);
    Category findCategoryById(Long id);
}
