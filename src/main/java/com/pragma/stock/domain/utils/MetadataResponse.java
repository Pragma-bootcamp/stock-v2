package com.pragma.stock.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetadataResponse {
    private  int currentPage;
    private  Long totalItems;
    private  int totalPages;
    private  int pageSize;
}
