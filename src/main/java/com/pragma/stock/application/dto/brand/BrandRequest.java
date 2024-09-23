package com.pragma.stock.application.dto.brand;

import com.pragma.stock.domain.constant.ErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    @NotNull(message = ErrorMessages.BRAND_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ErrorMessages.BRAND_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = ErrorMessages.BRAND_FIELD_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = ErrorMessages.BRAND_FIELD_DESCRIPTION_NOT_EMPTY)
    private String description;
}
