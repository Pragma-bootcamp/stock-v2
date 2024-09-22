package com.pragma.stock.infraestructure.input.rest;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.handler.article.IArticleHandler;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.documentation.ArticleResponseListApiFormat;
import com.pragma.stock.infraestructure.documentation.ArticleResponseUniqueApiFormat;
import com.pragma.stock.infraestructure.documentation.BrandReponseListApiFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@Tag(name = "Article")
@RequiredArgsConstructor
public class ArticleController {
    private final IArticleHandler iArticleHandler;

    @Operation(summary = "Create article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponseUniqueApiFormat.class))}),
            @ApiResponse(responseCode = "409", description = "The article already exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "ArticleException",
                                    summary = "Example response when the article already exists",
                                    value = "{ \"status\": 409, \"message\": \"The article 'Electronics' already exists.\" }"))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "ArticleException",
                                    summary = "Example response when there are some invalid properties",
                                    value = "{ \"status\": 409, \"message\": \"The field 'name' must not be null.\" }"))}),
    })
    @PostMapping()
    public ApiResponseFormat<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        return iArticleHandler.saveArticle(articleRequest);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Article",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponseListApiFormat.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "PaginationException",
                                    summary = "Example response when there are some invalid properties",
                                    value = "{ \"status\": 409, \"message\": \"The param 'page' must not be null.\" }"))}),
    })
    @GetMapping()
    public ApiResponseFormat<List<ArticleResponse>> listArticles(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "filterBy", required = false) String filterBy,
            @RequestParam(value = "filterValue",required = false) String filterValue
    ){
        return iArticleHandler.listArticles(page, size, sortDir, sortBy, filterBy, filterValue);
    }


}
