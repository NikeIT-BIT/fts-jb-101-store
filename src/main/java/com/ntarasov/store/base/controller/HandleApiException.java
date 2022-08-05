package com.ntarasov.store.base.controller;


import com.ntarasov.store.auth.exceptions.AuthException;
import com.ntarasov.store.auth.exceptions.NotAccessException;
import com.ntarasov.store.base.api.response.ErrorResponse;
import com.ntarasov.store.admin.exception.AdminExistException;
import com.ntarasov.store.admin.exception.AdminNotExistException;
import com.ntarasov.store.category.exception.CategoryExistException;
import com.ntarasov.store.category.exception.CategoryNotExistException;
import com.ntarasov.store.photo.exception.PhotoExistException;
import com.ntarasov.store.photo.exception.PhotoNotExistException;
import com.ntarasov.store.product.exception.ProductExistException;
import com.ntarasov.store.product.exception.ProductNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class HandleApiException extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    //    -------------------------------------Стандартные----------------------------------------------------
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("NotFoundExeption", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> badRequest(AdminNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("ResponseStatusException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("Exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> authException(AuthException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("AuthException", HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(NotAccessException.class)
    public ResponseEntity<Object> notAccessException(NotAccessException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("NotAccessException", HttpStatus.FORBIDDEN));
    }

    //    -------------------------------------ПОЛЬЗОВАТЕЛЬСКИЕ----------------------------------------------------
    @ExceptionHandler(AdminNotExistException.class)
    public ResponseEntity<Object> userNotExistException(AdminNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AdminExistException.class)
    public ResponseEntity<Object> userExistException(AdminExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CategoryNotExistException.class)
    public ResponseEntity<Object> categoryNotExistException(CategoryNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("CategoryNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CategoryExistException.class)
    public ResponseEntity<Object> categoryExistException(CategoryExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("CategoryExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PhotoNotExistException.class)
    public ResponseEntity<Object> photoNotExistException(PhotoNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("PhotoNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PhotoExistException.class)
    public ResponseEntity<Object> photoExistException(PhotoExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("PhotoExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<Object> productNotExistException(ProductNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("ProductNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ProductExistException.class)
    public ResponseEntity<Object> productExistException(ProductExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("ProductExistException", HttpStatus.BAD_REQUEST));
    }


}
