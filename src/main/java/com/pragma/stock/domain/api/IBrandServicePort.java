package com.pragma.stock.domain.api;

import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IBrandServicePort {
    ApiResponseFormat<Brand> saveBrand(Brand brand);
}
