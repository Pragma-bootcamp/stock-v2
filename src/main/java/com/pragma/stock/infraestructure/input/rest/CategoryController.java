package com.pragma.stock.infraestructure.input.rest;

import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.handler.category.ICategoryHandler;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.documentation.CategoryResponseListApiFormat;
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
@RequestMapping("/categories")
@Tag(name = "Category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "The category already exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "CategoryException",
                                    summary = "Example response when the category already exists",
                            value = "{ \"status\": 409, \"message\": \"The category 'Electronics' already exists.\" }"))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "CategoryException",
                                    summary = "Example response when some properties are invalid",
                            value = "{ \"status\": 409, \"message\": \"The category 'Electronics' already exists.\" }"))}),
    })
    @PostMapping()
    public ApiResponseFormat<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryHandler.saveCategory(categoryRequest);
    }
    @Operation(summary = " List all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List categories",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResponseListApiFormat.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping()
    public ApiResponseFormat<List<CategoryResponse>> listAllCategories(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC",required = false) String sortDir
    ) {
        return categoryHandler.findAllCategories(page,size,sortDir);
    }
}
