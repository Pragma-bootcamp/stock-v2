package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;

import java.util.List;

public interface IBrandHandler {
    ApiResponseFormat<BrandResponse> saveBrand(BrandRequest brandRequest);
    ApiResponseFormat<List<BrandResponse>> getAllBrands(int page,int size,String sortDir,String sortBy);
}
