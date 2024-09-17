package com.pragma.stock.infraestructure.input.rest;

import com.pragma.stock.application.dto.article.ArticleRequest;
import com.pragma.stock.application.dto.article.ArticleResponse;
import com.pragma.stock.application.handler.article.IArticleHandler;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.documentation.ArticleResponseUniqueApiFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/articles")
@Tag(name = "Article")
@RequiredArgsConstructor
public class ArticleController  {
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
    public ApiResponseFormat<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest){
        return iArticleHandler.saveArticle(articleRequest);
    }



}
