package com.pragma.stock.infraestructure.documentation;


import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;

public class CategoryResponseUniqueApiFormat extends ApiResponseFormat<CategoryResponse> {
    public CategoryResponseUniqueApiFormat(CategoryResponse data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
