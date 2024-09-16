package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandUseCaseTest {
    @Mock
    private IBrandServicePort brandServicePort;
    @Mock
    private IBrandPersistencePort iBrandPersistencePort;
    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrand() {
        Brand brand = mock(Brand.class);
        ApiResponseFormat<Brand> response = new ApiResponseFormat<>(brand,null);
        when(iBrandPersistencePort.saveBrand(brand)).thenReturn(response);
        ApiResponseFormat<Brand> result = brandUseCase.saveBrand(brand);
        assertNotNull(result);
    }
}