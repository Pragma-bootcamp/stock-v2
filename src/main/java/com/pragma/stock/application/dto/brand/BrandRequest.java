package com.pragma.stock.application.dto.brand;

import com.pragma.stock.domain.constant.BrandConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    @NotNull(message = com.pragma.stock.domain.constant.BrandConstant.BRAND_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = BrandConstant.BRAND_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = BrandConstant.BRAND_FIELD_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = BrandConstant.BRAND_FIELD_DESCRIPTION_NOT_EMPTY)
    private String description;
}
