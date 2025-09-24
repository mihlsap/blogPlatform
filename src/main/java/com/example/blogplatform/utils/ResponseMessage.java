package com.example.blogplatform.utils;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {

    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "Author not found."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input."),
    AUTHOR_UPDATED(HttpStatus.OK, "Author updated."),
    AUTHOR_REMOVED(HttpStatus.OK, "Author removed."),
    AUTHOR_FOUND(HttpStatus.OK, "Author found."),
    AUTHOR_ADDED(HttpStatus.CREATED, "Author added."),
    AUTHORS_FOUND(HttpStatus.OK, "Authors found."),
    AUTHORS_NOT_FOUND(HttpStatus.NOT_FOUND, "Authors not found.");

    public final String message;
    public final HttpStatus status;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
