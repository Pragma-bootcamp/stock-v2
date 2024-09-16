package com.pragma.stock.infraestructure.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.application.dto.category.CategoryRequest;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.handler.brand.IBrandHandler;
import com.pragma.stock.domain.model.Brand;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IBrandHandler iBrandHandler;
    ObjectMapper objectMapper = new ObjectMapper();
    private Brand brand;
    private BrandRequest brandRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBrand() throws Exception {
        BrandRequest brandRequest = new BrandRequest(Constant.DEFAULT_NAME, Constant.DEFAULT_DESCRIPTION);
        BrandResponse brand = new BrandResponse(0L,Constant.DEFAULT_NAME, Constant.DEFAULT_DESCRIPTION);
        ApiResponseFormat<BrandResponse> mockResponse = new ApiResponseFormat<>(brand,null);
        given(iBrandHandler.saveBrand(ArgumentMatchers.any())).willReturn(mockResponse);
        ResultActions response = mockMvc.perform(post("/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandRequest)));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.metadata").doesNotExist());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(Constant.DEFAULT_NAME));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data.description").value(Constant.DEFAULT_DESCRIPTION));
    }
}