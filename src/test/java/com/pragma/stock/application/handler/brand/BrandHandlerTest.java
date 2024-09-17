package com.pragma.stock.application.handler.brand;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.application.exception.PaginationException;
import com.pragma.stock.application.mapper.brand.BrandRequestMapper;
import com.pragma.stock.application.mapper.brand.BrandResponseMapper;
import com.pragma.stock.application.utils.UtilConstant;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandHandlerTest {
    @Mock
    private IBrandServicePort brandServicePort;
    @Mock
    private BrandRequestMapper brandRequestMapper;
    @Mock
    private BrandResponseMapper brandResponseMapper;
    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrand() {
        BrandRequest brandRequest = mock(BrandRequest.class);
        Brand brand = mock(Brand.class);
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        BrandResponse brandResponse = mock(BrandResponse.class);
        ApiResponseFormat<Brand> response = new ApiResponseFormat<>(brand,null);
        when(brandRequestMapper.toDomain(brandRequest)).thenReturn(brand);
        when(brandServicePort.saveBrand(brand)).thenReturn(response);
        when(brandResponseMapper.toDto(brand)).thenReturn(brandResponse);
        ApiResponseFormat<BrandResponse> result = brandHandler.saveBrand(brandRequest);
        assertNotNull(result);
    }

    @Test
    void getAllBrands() {
        int page = Constant.PAGE_DEFAULT;
        int size = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        Brand brand = mock(Brand.class);
        BrandResponse brandResponse = mock(BrandResponse.class);
        List<Brand> listBrands = List.of(mock(Brand.class),mock(Brand.class));
        MetadataResponse metadata = mock(MetadataResponse.class);
        ApiResponseFormat<List<Brand>> mockResponse = new ApiResponseFormat<>(listBrands,metadata);
        when(brandServicePort.getAllBrands(page, size, sortDir)).thenReturn(mockResponse);
        when(brandResponseMapper.toDto(brand)).thenReturn(brandResponse);
        ApiResponseFormat<List<BrandResponse>> apiResponseFormat = brandHandler.getAllBrands(page, size, sortDir);
        assertNotNull(apiResponseFormat);

    }

    @Test
    void getAllBrandsBadPageSize(){
        int page = Constant.WRONG_PAGE;
        int size = Constant.WRONG_PAGE_SIZE;
        String sortDir = Constant.WRONG_PAGE_SORT_DIR;
        PaginationException exception = assertThrows(PaginationException.class,()->{
            brandHandler.getAllBrands(page, size, sortDir);
        });
        assertEquals(UtilConstant.PAGINATION_NEGATIVE, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }
    @Test
    void getAllCategoriesBadSortDirSize(){
        int page = Constant.PAGE_DEFAULT;
        int size = Constant.PAGE_SIZE;
        String sortDir = Constant.WRONG_PAGE_SORT_DIR;
        PaginationException exception = assertThrows(PaginationException.class,()->{
            brandHandler.getAllBrands(page, size, sortDir);
        });
        assertEquals(UtilConstant.ORDER_DIR_VALID_OPTIONS, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }
}