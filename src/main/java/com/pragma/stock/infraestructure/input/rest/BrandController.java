package com.pragma.stock.infraestructure.input.rest;

import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;

import com.pragma.stock.application.handler.brand.IBrandHandler;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.infraestructure.documentation.BrandReponseListApiFormat;
import com.pragma.stock.infraestructure.documentation.BrandResponseUniqueApiFormat;
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
@RequestMapping("/brands")
@Tag(name = "Brand")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandHandler iBrandHandler;
    @Operation(summary = "Create brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandResponseUniqueApiFormat.class))}),
            @ApiResponse(responseCode = "409", description = "The brand already exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "CategoryException",
                                    summary = "Example response when the category already exists",
                            value = "{ \"status\": 409, \"message\": \"The brand 'Electronics' already exists.\" }"))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "CategoryException",
                                    summary = "Example response when there are some invalid properties",
                            value = "{ \"status\": 409, \"message\": \"The field 'name' must not be null.\" }"))}),
    })
    @PostMapping()
    public ApiResponseFormat<BrandResponse> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        return iBrandHandler.saveBrand(brandRequest);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Brand",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandReponseListApiFormat.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "PaginationException",
                                    summary = "Example response when there are some invalid properties",
                                    value = "{ \"status\": 409, \"message\": \"The param 'page' must not be null.\" }"))}),
    })
    @GetMapping()
    public ApiResponseFormat<List<BrandResponse>> listBrands (
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC",required = false) String sortDir,
            @RequestParam(value ="sortBy", required = false) String sortBy
    ){
        return iBrandHandler.getAllBrands(page, size, sortDir,sortBy);
    }
}
