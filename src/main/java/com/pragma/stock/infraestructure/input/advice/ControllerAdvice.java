package com.pragma.stock.infraestructure.input.advice;

import com.pragma.stock.application.exception.PaginationException;
import com.pragma.stock.application.utils.UtilConstant;
import com.pragma.stock.domain.utils.ErrorResponse;
import com.pragma.stock.domain.exception.CategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorResponse> handleCategoryException(CategoryException categoryException) {
        ErrorResponse errorResponse = new ErrorResponse(categoryException.getErrorMessage(), categoryException.getErrorCode());
        return ResponseEntity.status(categoryException.getErrorCode()).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append("\n");
        });
        ErrorResponse errResponse = new ErrorResponse(errors.toString().trim(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format(UtilConstant.INVALID_PARAMETER_VALUE, ex.getName(), ex.getMostSpecificCause().getMessage());
        ErrorResponse response = new ErrorResponse(errorMessage,HttpStatus.BAD_REQUEST.value() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MissingServletRequestParameterException ex) {
        ErrorResponse errResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }
    @ExceptionHandler(PaginationException.class)
    public ResponseEntity<ErrorResponse> handleArticleException(PaginationException paginationException) {
        ErrorResponse errorResponse = new ErrorResponse(paginationException.getErrorMessage(), paginationException.getErrorCode());
        return ResponseEntity.status(paginationException.getErrorCode()).body(errorResponse);
    }
}
