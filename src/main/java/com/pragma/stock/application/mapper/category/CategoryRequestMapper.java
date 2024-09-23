package com.pragma.stock.application.mapper.category;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {
    @Mapping(source = "name", target = "name" )
    @Mapping(source = "description", target = "description")
    Category toDomain(CategoryRequest category);

    @Named(value = "categoryIdsToCategories")
    default List<Category> mapCategoryIdsToCategories(List<Long> categoryIds) {
        if (categoryIds == null) {
            return List.of();
        }
        return categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                }).toList();
    }
}
