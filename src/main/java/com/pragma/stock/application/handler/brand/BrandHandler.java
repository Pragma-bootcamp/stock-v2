package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.application.mapper.brand.BrandRequestMapper;
import com.pragma.stock.application.mapper.brand.BrandResponseMapper;
import com.pragma.stock.domain.utils.UtilConstant;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{
    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public ApiResponseFormat<BrandResponse> saveBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toDomain(brandRequest);
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        ApiResponseFormat<Brand> brandSaved = brandServicePort.saveBrand(brand);
        BrandResponse brandResponse = brandResponseMapper.toDto(brandSaved.getData());
        return new ApiResponseFormat<>(brandResponse,null);
    }

    @Override
    public ApiResponseFormat<List<BrandResponse>> getAllBrands(int page, int size, String sortDir,String sortBy) {
        this.validatePagination(page, size, sortDir);
        ApiResponseFormat<List<Brand>> brands = brandServicePort.getAllBrands(page,size,sortDir,sortBy);
        List<BrandResponse> brandResponseList = brands.getData().stream().map(brandResponseMapper::toDto).toList();
        return new ApiResponseFormat<>(brandResponseList,brands.getMetadata());
    }

    public void validatePagination(int page,int size, String sortDir) {
        int minValue = UtilConstant.MIN_VALUE_PAGE_SIZE;
        if (page < minValue || size < minValue) {
            throw new PaginationException(HttpStatus.BAD_REQUEST.value(), UtilConstant.PAGINATION_NEGATIVE);
        }
        if(!(sortDir.equalsIgnoreCase(Element.ASC.name()) || sortDir.equalsIgnoreCase(Element.DESC.name()))) {
            throw new PaginationException(HttpStatus.BAD_REQUEST.value(), UtilConstant.ORDER_DIR_VALID_OPTIONS);
        }
    }
}
