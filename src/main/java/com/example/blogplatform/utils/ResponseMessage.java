package com.example.blogplatform.utils;

import org.springframework.http.HttpStatus;

/**
 * Enum representing standardized HTTP response statuses and messages
 * used throughout the blog platform's API responses.
 * <p>
 * Each constant maps to an {@link HttpStatus} and a descriptive message
 * to maintain consistency across the API.
 */
public enum ResponseMessage {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input."),
    CATEGORY_FOUND(HttpStatus.OK, "Category found."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found."),
    CATEGORY_ADDED(HttpStatus.CREATED, "Category added."),
    CATEGORY_UPDATED(HttpStatus.OK, "Category updated."),
    CATEGORY_REMOVED(HttpStatus.NO_CONTENT, "Category removed."),
    CATEGORIES_FOUND(HttpStatus.OK, "Categories found."),
    NO_CATEGORIES_FOUND(HttpStatus.NOT_FOUND, "No categories found.");

    public final String message;
    public final HttpStatus status;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
