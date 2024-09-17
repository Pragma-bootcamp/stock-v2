package com.pragma.stock.infraestructure.out.jpa.adapter;

import com.pragma.stock.domain.constant.CategoryConstant;
import com.pragma.stock.domain.exception.CategoryException;
import com.pragma.stock.domain.model.Category;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.domain.utils.MetadataResponse;
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
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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
        Category category = mock(Category.class);
        CategoryEntity categoryEntity= mock(CategoryEntity.class);
        when(categoryDboMapper.toDbo(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn( categoryEntity);
        ApiResponseFormat<Category> response = categoryJpaAdapter.saveCategory(category);
        assertNotNull(response);

    }
    @Test
    void saveCategoryWithDuplicateValues() {
        Category category = mock(Category.class);
        when(category.getName()).thenReturn(Constant.DEFAULT_NAME);
        List<CategoryEntity> existingCategories = List.of(mock(CategoryEntity.class));
        when(categoryRepository.findByName(Constant.DEFAULT_NAME)).thenReturn(existingCategories);
        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoryJpaAdapter.saveCategory(category);
        });
        assertEquals(HttpStatus.CONFLICT.value(), exception.getErrorCode());
        assertEquals(String.format(CategoryConstant.CATEGORY_ALREADY_EXIST,Constant.DEFAULT_NAME), exception.getMessage());
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void findAllCategories() {
        int page = Constant.PAGE_DEFAULT;
        int pageSize = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;

        CategoryEntity categoryEntity1 = mock(CategoryEntity.class);
        CategoryEntity categoryEntity2 = mock(CategoryEntity.class);
        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), Element.NAME.name().toLowerCase()));
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(categoryEntities);
        Category category = mock(Category.class);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryEntityPage);
        when(categoryDboMapper.toDbo(category)).thenReturn(categoryEntity);
        ApiResponseFormat<List<Category>> categories = categoryJpaAdapter.findAllCategories(page, pageSize, sortDir);
        assertNotNull(categories);

        assertEquals(2, categories.getData().size());
        verify(categoryRepository).findAll(argThat((Pageable p) ->
                p.getPageNumber() == page &&
                        p.getPageSize() == pageSize &&
                        Objects.requireNonNull(p.getSort().getOrderFor(Element.NAME.name().toLowerCase())).getDirection()== Sort.Direction.ASC
        ));

        MetadataResponse metadata = categories.getMetadata();
        assertNotNull(metadata);
        assertEquals(page, metadata.getCurrentPage());
        assertEquals(Constant.TOTAL_ELEMENTS, metadata.getTotalItems());
        assertEquals(Constant.TOTAL_PAGES_DEFAULT, metadata.getTotalPages());
        assertEquals(pageSize, metadata.getPageSize());
    }
}