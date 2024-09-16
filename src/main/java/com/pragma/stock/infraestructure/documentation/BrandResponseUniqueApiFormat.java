package com.pragma.stock.infraestructure.documentation;

import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;

public class BrandResponseUniqueApiFormat extends ApiResponseFormat<BrandResponse> {
    public BrandResponseUniqueApiFormat(BrandResponse data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
