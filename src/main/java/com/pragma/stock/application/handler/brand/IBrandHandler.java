package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

public interface IBrandHandler {
    ApiResponseFormat<BrandResponse> saveBrand(BrandRequest brandRequest);
}
