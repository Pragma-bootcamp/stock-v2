package com.pragma.stock.application.dto.category;

import com.pragma.stock.domain.constant.CategoryConstant;
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
public class CategoryRequest {
    @NotNull(message = com.pragma.stock.domain.constant.CategoryConstant.CATEGORY_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = CategoryConstant.CATEGORY_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = CategoryConstant.CATEGORY_FIELD_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = CategoryConstant.CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY)
    private String description;
}
