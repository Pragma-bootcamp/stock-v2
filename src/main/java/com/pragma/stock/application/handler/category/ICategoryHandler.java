package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface ICategoryHandler {
    ApiResponseFormat<CategoryResponse> saveCategory(CategoryRequest categoryRequest);

    ApiResponseFormat<List<CategoryResponse>> findAllCategories(int page, int size, String sortDir, String sortBy);
}
