package com.pragma.stock.domain.usecase;

import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.constant.BrandConstant;
import com.pragma.stock.domain.constant.ErrorCodeConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.BrandException;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandUseCaseTest {
    @Mock
    private IBrandServicePort brandServicePort;
    @Mock
    private IBrandPersistencePort iBrandPersistencePort;
    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrand() {
        Brand brand = new Brand();
        brand.setDescription(Constant.DEFAULT_DESCRIPTION);
        brand.setName(Constant.DEFAULT_NAME);
        ApiResponseFormat<Brand> response = new ApiResponseFormat<>(brand,null);
        when(iBrandPersistencePort.saveBrand(brand)).thenReturn(response);
        ApiResponseFormat<Brand> result = brandUseCase.saveBrand(brand);
        assertNotNull(result);
    }

    @Test
    void saveBrandExceptionShortName() {
        Brand brand = new Brand();
        brand.setDescription(Constant.DEFAULT_DESCRIPTION);
        brand.setName(Constant.DEFAULT_NAME.substring(0, 1));
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_NAME_LENGTH_MESSAGE, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
    }
    @Test
    void saveBrandExceptionEmptyName() {
        Brand brand = new Brand();
        brand.setDescription(Constant.DEFAULT_DESCRIPTION);
        brand.setName("");
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_FIELD_NAME_NOT_EMPTY, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }
    @Test
    void saveBrandExceptionNullName() {
        Brand brand = new Brand();
        brand.setDescription(Constant.DEFAULT_DESCRIPTION);
        brand.setName(null);
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_FIELD_NAME_NOT_NULL, exception.getErrorMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(),exception.getErrorCode());
    }

    @Test
    void saveBrandExceptionShortDescription() {
        Brand brand = new Brand();
        brand.setDescription(Constant.DEFAULT_DESCRIPTION.substring(0,1));
        brand.setName(Constant.DEFAULT_NAME);
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_DESCRIPTION_LENGTH_MESSAGE, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
    }
    @Test
    void saveBrandExceptionEmptyDescription() {
        Brand brand = new Brand();
        brand.setDescription("");
        brand.setName(Constant.DEFAULT_NAME);
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_FIELD_DESCRIPTION_NOT_EMPTY, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
    }
    @Test
    void saveBrandExceptionNullDescription() {
        Brand brand = new Brand();
        brand.setDescription(null);
        brand.setName(Constant.DEFAULT_NAME);
        BrandException exception = assertThrows(BrandException.class,()->{
            brandUseCase.saveBrand(brand);
        });
        assertEquals(BrandConstant.BRAND_FIELD_DESCRIPTION_NOT_NULL, exception.getErrorMessage());
        assertEquals(ErrorCodeConstant.BAD_REQUEST,exception.getErrorCode());
    }

    @Test
    void getAllBrands() {
        List<Brand> brands = List.of(mock(Brand.class),mock(Brand.class),mock(Brand.class));
        MetadataResponse metadata = new MetadataResponse(Constant.PAGE_DEFAULT,
                Constant.TOTAL_ELEMENTS,
                Constant.TOTAL_PAGES_DEFAULT,
                Constant.PAGE_SIZE);
        when(iBrandPersistencePort.findAllBrands(Constant.PAGE_DEFAULT,Constant.PAGE_SIZE,Constant.ORDER_ASC))
                .thenReturn(new ApiResponseFormat<>(brands,metadata));
        ApiResponseFormat<List<Brand>> listed = brandUseCase
                .getAllBrands(Constant.PAGE_DEFAULT,Constant.PAGE_SIZE,Constant.ORDER_ASC);
        assertNotNull(listed);

    }
}