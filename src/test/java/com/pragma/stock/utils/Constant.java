package com.pragma.stock.utils;

import java.util.List;

public class Constant {
    private Constant() {
    }

    public static final int PAGE_DEFAULT = 1;
    public static final int PAGE_SIZE = 2;
    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";
    public static final int WRONG_PAGE = -1;
    public static final int WRONG_PAGE_SIZE = -1;
    public static final String WRONG_PAGE_SORT_DIR = "Invalid";
    public static final String SORT_NAME_PARAM = "sortDir";
    public static final String DEFAULT_NAME = "default";
    public static final String DEFAULT_DESCRIPTION = "DESCRIPTION";
    public static final int TOTAL_PAGES_DEFAULT = 1;
    public static final Long TOTAL_ELEMENTS = 2L;
    public static final int DEFAULT_QUANTITY = 20;
    public static final double DEFAULT_PRICE = 20.5;
    public static final List<Long> DEFAULT_LIST_CATEGORIES = List.of(1L, 2L);
    public static final Long DEFAULT_BRAND = 1L;
    public static final Long DEFAULT_ID = 0L;
}
