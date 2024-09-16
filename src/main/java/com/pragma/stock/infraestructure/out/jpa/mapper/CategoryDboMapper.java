package com.pragma.stock.infraestructure.out.jpa.mapper;

import com.pragma.stock.domain.model.Category;
import com.pragma.stock.infraestructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CategoryDboMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    CategoryEntity toDbo(Category category);

    @InheritInverseConfiguration
    Category toDomain(CategoryEntity entity);
}
