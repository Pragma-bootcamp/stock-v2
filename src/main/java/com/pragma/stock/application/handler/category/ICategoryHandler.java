package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface ICategoryHandler {
    ApiResponseFormat<CategoryResponse> saveCategory(CategoryRequest categoryRequest);
}
