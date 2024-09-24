package com.pragma.stock.infraestructure.out.jpa.adapter;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                    String.format(ErrorMessages.BRAND_ALREADY_EXIST,brand.getName()));
        }
        BrandEntity brandToCreate = brandDboMapper.toDbo(brand);
        BrandEntity savedBrand = brandRepository.save(brandToCreate);
        Brand brandSaved = brandDboMapper.toDomain(savedBrand);
        return new ApiResponseFormat<>(brandSaved,null);
    }

    @Override
    public ApiResponseFormat<List<Brand>> findAllBrands(int page, int size, String sortDir,String sortBy) {
        Pageable pageable = PageRequest.of(page,size,
                Sort.by(Sort.Direction.fromString(sortDir),
                        sortBy!=null && !sortBy.isEmpty()? sortBy:Element.ID.name().toLowerCase()));
        Page<BrandEntity> brandPage = brandRepository.findAll(pageable);
        List<Brand> brands = brandPage.getContent().stream().map(brandDboMapper::toDomain).toList();
        MetadataResponse metadata = new MetadataResponse(page, brandPage.getTotalElements(),
                brandPage.getTotalPages(),size);
        return new ApiResponseFormat<>(brands,metadata);
    }

    @Override
    public Brand findBrandById(Long id) {
        BrandEntity brandEntity = brandRepository.findById(id).orElse(null);
        if (brandEntity == null) throw new BrandException(HttpStatus.NOT_FOUND.value(),
                String.format(ErrorMessages.BRAND_NOT_FOUND,id));
        return brandDboMapper.toDomain(brandEntity);
    }


}
