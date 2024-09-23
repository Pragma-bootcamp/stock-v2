package com.pragma.stock.infraestructure.out.jpa.adapter;
import com.pragma.stock.domain.constant.ErrorMessages;
import com.pragma.stock.domain.exception.ArticleException;
import com.pragma.stock.domain.model.Article;
import com.pragma.stock.domain.spi.IArticlePersistencePort;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.Element;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.infraestructure.out.jpa.entity.ArticleEntity;
import com.pragma.stock.infraestructure.out.jpa.mapper.ArticleDboMapper;
import com.pragma.stock.infraestructure.out.jpa.mapper.BrandDboMapper;
import com.pragma.stock.infraestructure.out.jpa.mapper.CategoryDboMapper;
import com.pragma.stock.infraestructure.out.jpa.repository.ArticleRepository;
import com.pragma.stock.infraestructure.out.jpa.repository.BrandRepository;
import com.pragma.stock.infraestructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor

public class ArticleJpaAdapter implements IArticlePersistencePort {
    private final ArticleRepository articleRepository;
    private final ArticleDboMapper articleDboMapper;

    @Override
    public ApiResponseFormat<Article> saveArticle(Article article) {
        List<ArticleEntity> existArticle = articleRepository.findByName(article.getName());
        if (!existArticle.isEmpty()) {
            throw new ArticleException(HttpStatus.CONFLICT.value(),
                    String.format(ErrorMessages.ARTICLE_ALREADY_EXIST, article.getName()));
        }
        ArticleEntity toCreate = articleDboMapper.toDbo(article);
        ArticleEntity createdArticle = articleRepository.save(toCreate);
        return new ApiResponseFormat<>(articleDboMapper.toDomain(createdArticle), null);
    }
    @Override
    public ApiResponseFormat<List<Article>> getArticles(int page, int size, String sortDir,
                                                        String sortBy, String filterBy, String filterValue) {
        Sort.Direction sortDirection = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(sortDirection, sortBy!= null ? sortBy : Element.ID.name().toLowerCase()));
        Page<ArticleEntity> entities = switch (filterBy == null? Element.NONE.name():filterBy) {
            case "categories" -> articleRepository.findByCategoryId(Long.parseLong(filterValue), pageable);
            case "brand" -> articleRepository.findByBrandName(filterValue, pageable);
            default -> articleRepository.findAll(pageable);
        };
        MetadataResponse metadata = new MetadataResponse(page, entities.getTotalElements(),
                entities.getTotalPages(), size);
        List<Article> articles = entities.getContent().stream().map(articleDboMapper::toDomain).toList();
        return new ApiResponseFormat<>(articles, metadata);
    }

    @Override
    public List<Article> getArticlesById(List<Long> ids) {
        return ids.stream().map(id -> {
            ArticleEntity articleEntity = articleRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ArticleException(HttpStatus.NOT_FOUND.value(), ErrorMessages.ARTICLE_NOT_FOUND)
                    );
            return articleDboMapper.toDomain(articleEntity);
        }).toList();
    }
}
