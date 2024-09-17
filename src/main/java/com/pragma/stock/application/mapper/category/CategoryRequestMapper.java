package com.pragma.stock.application.mapper.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {
    @Mapping(source = "name", target = "name" )
    @Mapping(source = "description", target = "description")
    Category toDomain(CategoryRequest category);
}
