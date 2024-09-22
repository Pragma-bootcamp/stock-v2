package com.pragma.stock.domain.constant;

public class ErrorMessages {
    private ErrorMessages() {}
    public static final String SORT_ARTICLE_BY_ERROR = "The field 'sortBy' must be 'name' only";
    public static final String FILTER_ARTICLE_BY_ERROR = "The field 'filterBy' must be 'name' 'brand' 'categories' only";
    public static final String FILTER_VALUE_NOT_BE_NULL = "The field 'filterValue' must not be value";
    public static final String CATEGORIES_MUST_BE_A_NUMBER = "The field 'categories' must be a number";
    public static final String SORT_DIR_ERROR = "The field 'sortDir' must be 'asc' or 'desc'";
}
