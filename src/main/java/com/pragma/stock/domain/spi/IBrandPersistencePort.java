package com.pragma.stock.domain.spi;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IBrandPersistencePort {
    ApiResponseFormat<Brand> saveBrand(Brand brand);
    ApiResponseFormat<List<Brand>> findAllBrands(int page,int size,String sortDir, String sortBy);
    Brand findBrandById(Long id);
}
