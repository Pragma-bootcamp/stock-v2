package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IBrandPersistencePort {
    ApiResponseFormat<Brand> saveBrand(Brand brand);
}
