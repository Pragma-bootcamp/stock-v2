package com.pragma.stock.infraestructure.documentation;

import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public class CategoryResponseListApiFormat extends ApiResponseFormat<List<CategoryResponse>> {
    public CategoryResponseListApiFormat(List<CategoryResponse> data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
