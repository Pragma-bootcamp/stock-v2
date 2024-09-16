package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.application.mapper.brand.BrandRequestMapper;
import com.pragma.stock.application.mapper.brand.BrandResponseMapper;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public ApiResponseFormat<BrandResponse> saveBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toDomain(brandRequest);
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        ApiResponseFormat<Brand> brandSaved = brandServicePort.saveBrand(brand);
        BrandResponse brandResponse = brandResponseMapper.toDto(brandSaved.getData());
        return new ApiResponseFormat<>(brandResponse,null);
    }
}
