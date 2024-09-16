package com.pragma.stock.application.mapper.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandRequestMapper {
    @Mapping(source = "name", target = "name" )
    @Mapping(source = "description", target = "description")
    Brand toDomain(BrandRequest brandRequest);
}
