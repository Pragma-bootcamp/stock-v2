package com.pragma.stock.infraestructure.out.jpa.mapper;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandDboMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    BrandEntity toDbo(Brand brand);

    @InheritInverseConfiguration
    Brand toDomain(BrandEntity brandEntity);
}
