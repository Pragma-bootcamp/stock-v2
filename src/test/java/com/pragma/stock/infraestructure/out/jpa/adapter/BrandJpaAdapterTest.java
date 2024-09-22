package com.pragma.stock.infraestructure.out.jpa.adapter;


import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.infraestructure.out.jpa.entity.BrandEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class BrandJpaAdapterTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandDboMapper brandDboMapper;
    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveBrand() {
        Brand brand = mock(Brand.class);
        BrandEntity brandEntity = mock(BrandEntity.class);
        List<BrandEntity> brandEntities = List.of();
        when(brandRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(brandEntities);
        when(brandDboMapper.toDbo(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandDboMapper.toDomain(brandEntity)).thenReturn(brand);
        ApiResponseFormat<Brand> result = brandJpaAdapter.saveBrand(brand);
        assertNotNull(result);
    }
    @Test
    void saveBrandWithDuplicatesBrand() {
        Brand brand = mock(Brand.class);
        List<BrandEntity> brandEntityList = List.of(mock(BrandEntity.class));
        when(brand.getName()).thenReturn(Constant.DEFAULT_NAME);
        when(brandRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(brandEntityList);
        BrandException exception = assertThrows(BrandException.class, () -> {
            brandJpaAdapter.saveBrand(brand);
        });
        assertEquals(HttpStatus.CONFLICT.value(),exception.getErrorCode());
        assertEquals(String.format(BrandConstant.BRAND_ALREADY_EXIST,Constant.DEFAULT_NAME),exception.getErrorMessage());
        verify(brandRepository, never()).save(any(BrandEntity.class));
    }
    @Test
    void findAllBrands() {
        int page = Constant.PAGE_DEFAULT;
        int pageSize = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        BrandEntity brandEntity1 = mock(BrandEntity.class);
        BrandEntity brandEntity2 = mock(BrandEntity.class);
        List<BrandEntity> brandEntities = List.of(brandEntity1, brandEntity2);
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), Element.NAME.name().toLowerCase()));
        Page<BrandEntity> brandEntityPage = new PageImpl<>(brandEntities);
        Brand brand = mock(Brand.class);
        BrandEntity brandEntity = mock(BrandEntity.class);

        when(brandRepository.findAll(pageable)).thenReturn(brandEntityPage);
        when(brandDboMapper.toDbo(brand)).thenReturn(brandEntity);

        ApiResponseFormat<List<Brand>> brands = brandJpaAdapter.findAllBrands(page,pageSize,sortDir);
        assertNotNull(brands);

        assertEquals(Constant.PAGE_SIZE, brands.getData().size());
        verify(brandRepository).findAll(argThat((Pageable p) ->
                p.getPageNumber() == page &&
                        p.getPageSize() == pageSize &&
                        Objects.requireNonNull(p.getSort().getOrderFor(Element.NAME.name().toLowerCase())).getDirection()== Sort.Direction.ASC
        ));

        MetadataResponse metadata = brands.getMetadata();
        assertNotNull(metadata);
        assertEquals(page, metadata.getCurrentPage());
        assertEquals(Constant.TOTAL_ELEMENTS, metadata.getTotalItems());
        assertEquals(Constant.TOTAL_PAGES_DEFAULT, metadata.getTotalPages());
        assertEquals(pageSize, metadata.getPageSize());

    }
}