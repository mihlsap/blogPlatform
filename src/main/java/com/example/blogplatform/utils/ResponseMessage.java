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
    CATEGORY_ADDED(HttpStatus.CREATED, "Category added."),
    CATEGORY_REMOVED(HttpStatus.NO_CONTENT, "Category removed."),
    CATEGORIES_FOUND(HttpStatus.OK, "Categories found."),
    NO_CATEGORIES_FOUND(HttpStatus.NOT_FOUND, "No categories found."),

    TAGS_FOUND(HttpStatus.OK, "Tags found."),
    NO_TAGS_FOUND(HttpStatus.NOT_FOUND, "No tags found."),
    TAG_REMOVED(HttpStatus.NO_CONTENT, "Tag removed.");

    public final String message;
    public final HttpStatus status;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
