package com.pragma.stock.infraestructure.configuration;

import com.pragma.stock.domain.api.IArticleServicePort;
import com.pragma.stock.domain.api.IBrandServicePort;
import com.pragma.stock.domain.api.ICategoryServicePort;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.spi.IBrandPersistencePort;
import com.pragma.stock.domain.spi.ICategoryPersistencePort;
import com.pragma.stock.domain.usecase.ArticleUseCase;
import com.pragma.stock.domain.usecase.BrandUseCase;
import com.pragma.stock.domain.usecase.CategoryUseCase;
import com.pragma.stock.infraestructure.out.jpa.adapter.ArticleJpaAdapter;
import com.pragma.stock.infraestructure.out.jpa.adapter.BrandJpaAdapter;
import com.pragma.stock.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.stock.infraestructure.out.jpa.mapper.ArticleDboMapper;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.ArticleRepository;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryDboMapper categoryDboMapper;
    private final BrandRepository brandRepository;
    private final BrandDboMapper brandDboMapper;
    private final ArticleRepository articleRepository;
    private final ArticleDboMapper articleDboMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository,categoryDboMapper);
    }
    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository,brandDboMapper);
    }
    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }
    @Bean
    public IArticlePersistencePort articlePersistencePort() {
        return new ArticleJpaAdapter(articleRepository,
                articleDboMapper);
    }
    @Bean
    public IArticleServicePort articleServicePort(){
        return new ArticleUseCase(articlePersistencePort(),categoryPersistencePort(),brandPersistencePort());
    }
}
