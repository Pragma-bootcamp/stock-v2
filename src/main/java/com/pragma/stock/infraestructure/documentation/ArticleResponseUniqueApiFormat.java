package com.pragma.stock.infraestructure.documentation;

import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;

public class ArticleResponseUniqueApiFormat extends ApiResponseFormat<ArticleResponse> {
    public ArticleResponseUniqueApiFormat(ArticleResponse data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
