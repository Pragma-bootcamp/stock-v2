package com.pragma.stock.domain.constant;

public class BrandConstant {
    public static final String BRAND_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String BRAND_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String BRAND_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String BRAND_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";
    public static final int BRAND_NAME_MAX_LENGTH = 50;
    public static final int BRAND_DESCRIPTION_MAX_LENGTH = 120;
    public static final int BRAND_NAME_MIN_LENGTH = 3;
    public static final int BRAND_DESCRIPTION_MIN_LENGTH = 3;
    public static final String BRAND_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public  static final String BRAND_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
    public static final String BRAND_ALREADY_EXIST = "The brand '%s' already exist";
}
