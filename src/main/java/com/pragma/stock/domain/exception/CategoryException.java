package com.pragma.stock.domain.exception;

import java.io.Serial;

public class CategoryException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int errorCode;
    private final String errorMessage;

    public CategoryException(final int errorCode,final String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
