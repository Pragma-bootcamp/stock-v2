package com.pragma.stock.domain.constant;

public class ArticleConstant {
    private ArticleConstant() {}
    public static final String ARTICLE_FIELD_NAME_NOT_NULL = "The field 'name' must not be null";
    public static final String ARTICLE_FIELD_DESCRIPTION_NOT_NULL = "The field 'description' must not be null";
    public static final String ARTICLE_FIELD_DESCRIPTION_NOT_EMPTY = "The field 'description' must not be empty";
    public static final String ARTICLE_FIELD_NAME_NOT_EMPTY = "The field 'name' must not be empty";
    public static final int ARTICLE_NAME_MAX_LENGTH = 50;
    public static final int ARTICLE_DESCRIPTION_MAX_LENGTH = 120;
    public static final int ARTICLE_NAME_MIN_LENGTH = 3;
    public static final int ARTICLE_DESCRIPTION_MIN_LENGTH = 3;
    public static final int ARTICLE_CATEGORIES_MAX_LENGTH = 3;
    public static final int ARTICLE_CATEGORIES_MIN_LENGTH = 1;
    public static final String ARTICLE_NAME_LENGTH_MESSAGE = "The length of field 'name' must be between 3 and 50";
    public  static final String ARTICLE_DESCRIPTION_LENGTH_MESSAGE = "The field 'description' must be between 3 and 90";
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

}
