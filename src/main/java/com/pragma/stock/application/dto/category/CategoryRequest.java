package com.pragma.stock.application.dto.category;

import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.constant.ErrorMessages;
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
    @NotNull(message = ErrorMessages.CATEGORY_FIELD_NAME_NOT_NULL)
    @NotEmpty(message = ErrorMessages.CATEGORY_FIELD_NAME_NOT_EMPTY)
    private String name;
    @NotNull(message = ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = ErrorMessages.CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY)
    private String description;
}
