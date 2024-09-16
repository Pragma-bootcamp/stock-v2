package com.pragma.stock.domain.constant;

public class CategoryConstant {
    private CategoryConstant(){}
    public static final String CATEGORY_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String CATEGORY_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String CATEGORY_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";
    public static final int CATEGORY_NAME_MAX_LENGTH = 50;
    public static final int CATEGORY_DESCRIPTION_MAX_LENGTH = 90;
    public static final int CATEGORY_NAME_MIN_LENGTH = 3;
    public static final int CATEGORY_DESCRIPTION_MIN_LENGTH = 3;
    public static final String CATEGORY_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public  static final String CATEGORY_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
    public static final String CATEGORY_ALREADY_EXIST = "The category '%s' already exist";

}
