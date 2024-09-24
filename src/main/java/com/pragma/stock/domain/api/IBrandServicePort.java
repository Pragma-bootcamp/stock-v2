package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IBrandServicePort {
    ApiResponseFormat<Brand> saveBrand(Brand brand);
    ApiResponseFormat<List<Brand>> getAllBrands(int page, int size, String sortDir,String sortBy);
    Brand getBrandById(Long id);
}
