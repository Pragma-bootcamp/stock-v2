package com.pragma.stock.infraestructure.out.jpa.adapter;


import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class BrandJpaAdapterTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandDboMapper brandDboMapper;
    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveBrand() {
        Brand brand = mock(Brand.class);
        BrandEntity brandEntity = mock(BrandEntity.class);
        List<BrandEntity> brandEntities = List.of();
        when(brandRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(brandEntities);
        when(brandDboMapper.toDbo(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandDboMapper.toDomain(brandEntity)).thenReturn(brand);
        ApiResponseFormat<Brand> result = brandJpaAdapter.saveBrand(brand);
        assertNotNull(result);
    }
    @Test
    void saveBrandWithDuplicatesBrand() {
        Brand brand = mock(Brand.class);
        List<BrandEntity> brandEntityList = List.of(mock(BrandEntity.class));
        when(brand.getName()).thenReturn(Constant.DEFAULT_NAME);
        when(brandRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(brandEntityList);
        BrandException exception = assertThrows(BrandException.class, () -> {
            brandJpaAdapter.saveBrand(brand);
        });
        assertEquals(HttpStatus.CONFLICT.value(),exception.getErrorCode());
        assertEquals(String.format(BrandConstant.BRAND_ALREADY_EXIST,Constant.DEFAULT_NAME),exception.getErrorMessage());
        verify(brandRepository, never()).save(any(BrandEntity.class));
    }
}