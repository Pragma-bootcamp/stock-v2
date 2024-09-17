package com.pragma.stock.infraestructure.documentation;

import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;

import java.util.List;

public class BrandReponseListApiFormat extends ApiResponseFormat<List<BrandResponse>> {
    public BrandReponseListApiFormat(List<BrandResponse> data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
