package com.pragma.stock.infraestructure.configuration;

import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.usecase.CategoryUseCase;
import com.pragma.stock.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryDboMapper categoryDboMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository,categoryDboMapper);
    }
    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

}
