package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface ICategoryServicePort {
    ApiResponseFormat<Category> saveCategory(Category category);
}
