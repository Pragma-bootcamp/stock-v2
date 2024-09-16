package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.mapper.CategoryRequestMapper;
import com.pragma.stock.application.mapper.CategoryResponseMapper;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public ApiResponseFormat<CategoryResponse> saveCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toDomain(categoryRequest);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        ApiResponseFormat<Category> categoryCreated = categoryServicePort.saveCategory(category);
        CategoryResponse categoryResponse = categoryResponseMapper.toDto(categoryCreated.getData());
        return new ApiResponseFormat<>(categoryResponse,null);
    }
}
