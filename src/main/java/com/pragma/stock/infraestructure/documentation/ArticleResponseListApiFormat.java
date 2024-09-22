package com.pragma.stock.infraestructure.documentation;

import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;

import java.util.List;

public class ArticleResponseListApiFormat extends ApiResponseFormat<List<ArticleResponse>> {
    public ArticleResponseListApiFormat(List<ArticleResponse> data, MetadataResponse metadata) {
        super(data, metadata);
    }
}
