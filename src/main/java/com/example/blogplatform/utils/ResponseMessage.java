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

    CATEGORY_ADDED(HttpStatus.CREATED, "Category added."),
    CATEGORIES_FOUND(HttpStatus.OK, "Categories found."),
    NO_CATEGORIES_FOUND(HttpStatus.NOT_FOUND, "No categories found."),
    CATEGORY_REMOVED(HttpStatus.NO_CONTENT, "Category removed."),

    TAG_ADDED(HttpStatus.CREATED, "Tag added."),
    TAGS_FOUND(HttpStatus.OK, "Tags found."),
    NO_TAGS_FOUND(HttpStatus.NOT_FOUND, "No tags found."),
    TAG_REMOVED(HttpStatus.NO_CONTENT, "Tag removed."),

    POST_ADDED(HttpStatus.CREATED, "Post added."),
    POSTS_FOUND(HttpStatus.OK, "Posts found."),
    NO_POSTS_FOUND(HttpStatus.NOT_FOUND, "No posts found."),
    POST_REMOVED(HttpStatus.NO_CONTENT, "Post removed.");

    public final String message;
    public final HttpStatus status;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
