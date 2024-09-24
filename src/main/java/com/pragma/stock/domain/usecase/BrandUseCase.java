package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.exception.PaginationException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort iBrandPersistencePort;

    public BrandUseCase(IBrandPersistencePort iBrandPersistencePort) {
        this.iBrandPersistencePort = iBrandPersistencePort;
    }
    @Override
    public ApiResponseFormat<Brand> saveBrand(Brand brand) {

        return iBrandPersistencePort.saveBrand(brand);
    }
    @Override
    public ApiResponseFormat<List<Brand>> getAllBrands(int page, int size, String sortDir, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals(Element.NAME.name().toLowerCase())) {
            throw new PaginationException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.BRAND_SORT_BY_NOT_ALLOW);
        }
        return iBrandPersistencePort.findAllBrands(page, size, sortDir,sortBy);
    }
    @Override
    public Brand getBrandById(Long id) {
        return iBrandPersistencePort.findBrandById(id);
    }

    public void validateBrand(Brand brand){
        if(brand.getName()==null ){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST, ErrorMessages.BRAND_FIELD_NAME_NOT_NULL);
        }
        if(brand.getName().isEmpty()){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.BRAND_FIELD_NAME_NOT_EMPTY);
        }
        if(brand.getDescription()==null ){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.BRAND_FIELD_DESCRIPTION_NOT_NULL);
        }
        if(brand.getDescription().isEmpty()){
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.BRAND_FIELD_DESCRIPTION_NOT_EMPTY);
        }
        if(brand.getDescription().length() > BrandConstant.BRAND_DESCRIPTION_MAX_LENGTH ||
                brand.getDescription().length() < BrandConstant.BRAND_DESCRIPTION_MIN_LENGTH) {
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.BRAND_DESCRIPTION_LENGTH_MESSAGE);
        }
        if(brand.getName().length() > BrandConstant.BRAND_NAME_MAX_LENGTH ||
                brand.getName().length() < BrandConstant.BRAND_NAME_MIN_LENGTH) {
            throw new BrandException(ErrorCodeConstant.BAD_REQUEST,ErrorMessages.BRAND_NAME_LENGTH_MESSAGE);
        }
    }

}
