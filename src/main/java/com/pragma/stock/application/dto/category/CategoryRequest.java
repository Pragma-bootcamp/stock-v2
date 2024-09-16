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
    @Size(max = CategoryConstant.CATEGORY_NAME_MAX_LENGTH, min = CategoryConstant.CATEGORY_NAME_MIN_LENGTH,
            message = CategoryConstant.CATEGORY_NAME_LENGTH_MESSAGE)
    @NotEmpty(message = CategoryConstant.CATEGORY_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = CategoryConstant.CATEGORY_FIELD_DESCRIPTION_NOT_NULL)
    @Size(max = CategoryConstant.CATEGORY_DESCRIPTION_MAX_LENGTH, min = CategoryConstant.CATEGORY_DESCRIPTION_MIN_LENGTH,
            message = CategoryConstant.CATEGORY_DESCRIPTION_LENGTH_MESSAGE)
    @NotEmpty(message = CategoryConstant.CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY)
    private String description;
}
