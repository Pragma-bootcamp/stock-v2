package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort iBrandPersistencePort;

    public BrandUseCase(IBrandPersistencePort iBrandPersistencePort) {
        this.iBrandPersistencePort = iBrandPersistencePort;
    }

    @Override
    public ApiResponseFormat<Brand> saveBrand(Brand brand) {
        return iBrandPersistencePort.saveBrand(brand);
    }
}
