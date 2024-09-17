package com.pragma.stock.infraestructure.input.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stock.application.dto.brand.BrandRequest;
import com.pragma.stock.application.dto.brand.BrandResponse;
import com.pragma.stock.application.dto.category.CategoryResponse;
import com.pragma.stock.application.handler.brand.IBrandHandler;
import com.pragma.stock.domain.utils.ApiResponseFormat;
import com.pragma.stock.domain.utils.MetadataResponse;
import com.pragma.stock.utils.Constant;
import com.pragma.stock.utils.Element;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IBrandHandler iBrandHandler;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBrand() throws Exception {
        BrandRequest brandRequest = new BrandRequest(Constant.DEFAULT_NAME, Constant.DEFAULT_DESCRIPTION);
        BrandResponse brand = new BrandResponse(0L, Constant.DEFAULT_NAME, Constant.DEFAULT_DESCRIPTION);
        ApiResponseFormat<BrandResponse> mockResponse = new ApiResponseFormat<>(brand, null);
        given(iBrandHandler.saveBrand(ArgumentMatchers.any())).willReturn(mockResponse);
        ResultActions response = mockMvc.perform(post("/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandRequest)));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.metadata").doesNotExist());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(Constant.DEFAULT_NAME));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data.description")
                .value(Constant.DEFAULT_DESCRIPTION));
    }

    @Test
    void listCategories() throws Exception {
        Integer page = Constant.PAGE_DEFAULT;
        Integer pageSize = Constant.PAGE_SIZE;
        String sortDir = Constant.ORDER_ASC;
        List<BrandResponse> mockList = List.
                of(new BrandResponse(0L, Constant.DEFAULT_NAME,
                        Constant.DEFAULT_DESCRIPTION), mock(BrandResponse.class));
        ApiResponseFormat<List<BrandResponse>> mockResponse = new ApiResponseFormat<>(mockList,
                new MetadataResponse(page, Constant.TOTAL_ELEMENTS, Constant.TOTAL_PAGES_DEFAULT, pageSize));
        given(iBrandHandler.getAllBrands(page, pageSize, sortDir)).willReturn(mockResponse);
        ResultActions response = mockMvc.perform(get("/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam(Element.SIZE.name().toLowerCase(), pageSize.toString())
                .queryParam(Element.PAGE.name().toLowerCase(), page.toString())
                .queryParam(Constant.SORT_NAME_PARAM, sortDir)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.metadata").exists());

    }
}