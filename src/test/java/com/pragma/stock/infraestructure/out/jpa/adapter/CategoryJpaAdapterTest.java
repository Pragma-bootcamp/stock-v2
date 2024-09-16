package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.application.utils.UtilConstant;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.infraestructure.out.jpa.entity.CategoryEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CategoryJpaAdapterTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryDboMapper categoryDboMapper;
    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveCategory() {
    }

    @Test
    void findAllCategories() {
        int page = Constant.PAGE_DEFAULT;
        int pageSize = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;

        CategoryEntity categoryEntity1 = mock(CategoryEntity.class);
        CategoryEntity categoryEntity2 = mock(CategoryEntity.class);
        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Pageable pageable = PageRequest.of(page,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), Element.NAME.name().toLowerCase()));
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(categoryEntities);
        Category category = mock(Category.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryEntityPage);
        when(categoryDboMapper.toDbo(category)).thenReturn(categoryEntity);
        ApiResponseFormat<List<Category>> categories = categoryJpaAdapter.findAllCategories(page,pageSize,sortDir);
        assertNotNull(categories);
    }
}