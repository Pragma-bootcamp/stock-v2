package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.application.mapper.brand.BrandRequestMapper;
import com.pragma.stock.application.mapper.brand.BrandResponseMapper;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandHandlerTest {
    @Mock
    private IBrandServicePort brandServicePort;
    @Mock
    private BrandRequestMapper brandRequestMapper;
    @Mock
    private BrandResponseMapper brandResponseMapper;
    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrand() {
        BrandRequest brandRequest = mock(BrandRequest.class);
        Brand brand = mock(Brand.class);
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        BrandResponse brandResponse = mock(BrandResponse.class);
        ApiResponseFormat<Brand> response = new ApiResponseFormat<>(brand,null);
        when(brandRequestMapper.toDomain(brandRequest)).thenReturn(brand);
        when(brandServicePort.saveBrand(brand)).thenReturn(response);
        when(brandResponseMapper.toDto(brand)).thenReturn(brandResponse);
        ApiResponseFormat<BrandResponse> result = brandHandler.saveBrand(brandRequest);
        assertNotNull(result);
    }

}