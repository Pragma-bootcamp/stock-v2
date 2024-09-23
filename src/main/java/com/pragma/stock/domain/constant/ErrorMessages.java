package com.pragma.stock.domain.constant;

public class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String SORT_ARTICLE_BY_ERROR = "The field 'sortBy' must be 'name' only";
    public static final String FILTER_ARTICLE_BY_ERROR = "The field 'filterBy' must be 'name' 'brand' 'categories' only";
    public static final String FILTER_VALUE_NOT_BE_NULL = "The field 'filterValue' must not be value";
    public static final String CATEGORIES_MUST_BE_A_NUMBER = "The field 'categories' must be a number";
    public static final String SORT_DIR_ERROR = "The field 'sortDir' must be 'asc' or 'desc'";

    public static final String ARTICLE_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String ARTICLE_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String ARTICLE_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String ARTICLE_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";
    public static final String ARTICLE_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public static final String ARTICLE_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
    public static final String ARTICLE_ALREADY_EXIST = "The brand '%s' already exist";
    public static final String ARTICLE_PRICE_NOT_NULL = "The field 'price' must not be null";
    public static final String ARTICLE_PRICE_POSITIVE = "The field 'price' must be a positive integer";
    public static final String ARTICLE_QUANTITY_NOT_NULL = "The field 'quantity' must not be null";
    public static final String ARTICLE_QUANTITY_POSITIVE = "The field 'quantity' must be greater than 0";
    public static final String ARTICLE_CATEGORIES_NOT_NULL = "The field 'categories' must not be null";
    public static final String ARTICLE_CATEGORIES_LENGTH = "The length of field 'categories' must be between 1 and 3";
    public static final String ARTICLE_CATEGORIES_NOT_EMPTY = "The field 'categories' must not be empty";
    public static final String ARTICLE_CATEGORIES_VALUES_TYPE = "The field 'categories' must have positive number only";
    public static final String ARTICLE_BRAND_NOT_NULL = "The field 'brand' must not be null";
    public static final String ARTICLE_BRAND_POSITIVE = "The field 'brand' must have values greater than 0";
    public static final String ARTICLE_CATEGORIES_DUPLICATED = "The categories ids can't be duplicated";
    public static final String ARTICLE_NOT_FOUND = "The article with the id '%d' not found";

    public static final String BRAND_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public static final String BRAND_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
    public static final String BRAND_ALREADY_EXIST = "The brand '%s' already exist";
    public static final String BRAND_NOT_FOUND = "The brand with the id '%d' does not exist";
    public static final String BRAND_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String BRAND_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String BRAND_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String BRAND_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";

    public static final String CATEGORY_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public static final String CATEGORY_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
    public static final String CATEGORY_ALREADY_EXIST = "The category '%s' already exist";
    public static final String CATEGORY_NOT_FOUND = "The category with the id '%d' does not exist";
    public static final String CATEGORY_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String CATEGORY_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String CATEGORY_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String CATEGORY_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";

}
