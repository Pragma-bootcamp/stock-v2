package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.CategoryException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import com.pragma.stock.domain.utils.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                String.format(ErrorMessages.CATEGORY_ALREADY_EXIST,category.getName()));
        CategoryEntity toCreate = mapper.toDbo(category);
        CategoryEntity created = categoryRepository.save(toCreate);
        return new ApiResponseFormat<>(mapper.toDomain(created),null);
    }

    @Override
    public ApiResponseFormat<List<Category>> findAllCategories(int page, int size, String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Direction.fromString(sortDir), Element.NAME.name().toLowerCase()));
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categories = categoryPage.getContent().stream().map(mapper::toDomain).toList();
        MetadataResponse metadata = new MetadataResponse(page,
                categoryPage.getTotalElements(),categoryPage.getTotalPages(),size);
        return new ApiResponseFormat<>(categories,metadata);
    }

    @Override
    public Category findCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        if(categoryEntity == null) throw  new CategoryException(HttpStatus.NOT_FOUND.value(),
                String.format(ErrorMessages.CATEGORY_NOT_FOUND,id));
        return mapper.toDomain(categoryEntity);
    }

}
