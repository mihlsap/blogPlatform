package com.example.blogplatform.utils;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input."),
    CATEGORY_FOUND(HttpStatus.OK, "Category found."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found."),
    CATEGORY_ADDED(HttpStatus.CREATED, "Category added."),
    CATEGORY_UPDATED(HttpStatus.OK, "Category updated."),
    CATEGORY_REMOVED(HttpStatus.OK, "Category removed."),
    CATEGORIES_FOUND(HttpStatus.OK, "Categories found."),
    CATEGORIES_NOT_FOUND(HttpStatus.NOT_FOUND, "Categories not found.");

    public final String message;
    public final HttpStatus status;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
