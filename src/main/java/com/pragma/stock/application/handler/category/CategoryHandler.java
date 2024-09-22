package com.pragma.stock.application.handler.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.application.mapper.category.CategoryRequestMapper;
import com.pragma.stock.application.mapper.category.CategoryResponseMapper;
import com.pragma.stock.application.utils.UtilConstant;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public ApiResponseFormat<List<CategoryResponse>> findAllCategories(int page,int size,String sortDir) {
        this.validatePagination(page,size,sortDir);
        ApiResponseFormat<List<Category>> categories = categoryServicePort.findAllCategories(page,size,sortDir);
        List<CategoryResponse> response = categories.getData().stream().map(categoryResponseMapper::toDto).toList();
        return new ApiResponseFormat<>(response,categories.getMetadata());
    }
    public void validatePagination(int page,int size, String sortDir) {
        if (page < UtilConstant.MIN_VALUE_PAGE_SIZE || size < UtilConstant.MIN_VALUE_PAGE_SIZE) {
            throw new PaginationException(HttpStatus.BAD_REQUEST.value(), UtilConstant.PAGINATION_NEGATIVE);
        }
        if(!(sortDir.equalsIgnoreCase(Element.ASC.name()) || sortDir.equalsIgnoreCase(Element.DESC.name()))) {
            throw new PaginationException(HttpStatus.BAD_REQUEST.value(), UtilConstant.ORDER_DIR_VALID_OPTIONS);
        }
    }
}
