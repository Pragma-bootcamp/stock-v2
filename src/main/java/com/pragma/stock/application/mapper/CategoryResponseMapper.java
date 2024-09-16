package com.pragma.stock.application.mapper;

import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "createdAt",target = "createdAt")
    @Mapping(source = "updatedAt",target = "updatedAt")
    CategoryResponse toDto(Category category);
}
