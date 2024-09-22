package com.pragma.stock.domain.utils;

public class UtilConstant {
    private UtilConstant() {
    }
    public static final String PAGINATION_NEGATIVE = "The param 'page' and 'size' must not be negative";
    public static final String PAGE_NULL = "The param 'page' is mandatory";
    public static final String SIZE_NULL = "The param 'size' is mandatory";
    public static final String ORDER_DIR_NULL = "The param 'orderDir' must not be null";
    public static final String ORDER_DIR_VALID_OPTIONS = "The param 'orderDir' options are 'asc' or 'desc' only";
    public static final int MIN_VALUE_PAGE_SIZE = 0;
    public static final String INVALID_PARAMETER_VALUE = "The param '%s' has a invalid value: '%s'";
}
