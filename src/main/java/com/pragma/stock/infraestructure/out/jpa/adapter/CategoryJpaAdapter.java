package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.exception.CategoryException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final CategoryDboMapper mapper;
    @Override
    public ApiResponseFormat<Category> saveCategory(Category category) {
        List<CategoryEntity> existCategory = categoryRepository.findByName(category.getName());
        if(!existCategory.isEmpty()) throw new CategoryException(HttpStatus.CONFLICT.value(),
                String.format(CategoryConstant.CATEGORY_ALREADY_EXIST,category.getName()));
        CategoryEntity toCreate = mapper.toDbo(category);
        CategoryEntity created = categoryRepository.save(toCreate);
        return new ApiResponseFormat<>(mapper.toDomain(created),null);
    }
}
