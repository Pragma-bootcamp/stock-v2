package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {
    private final BrandRepository brandRepository;
    private final BrandDboMapper brandDboMapper;
    @Override
    public ApiResponseFormat<Brand> saveBrand(Brand brand) {
        List<BrandEntity> brandExist = brandRepository.findByName(brand.getName());
        if (!brandExist.isEmpty()) {
            throw new BrandException(HttpStatus.CONFLICT.value(),
                    String.format(BrandConstant.BRAND_ALREADY_EXIST,brand.getName()));
        }
        BrandEntity brandToCreate = brandDboMapper.toDbo(brand);
        BrandEntity savedBrand = brandRepository.save(brandToCreate);
        Brand brandSaved = brandDboMapper.toDomain(savedBrand);
        return new ApiResponseFormat<>(brandSaved,null);
    }
}
