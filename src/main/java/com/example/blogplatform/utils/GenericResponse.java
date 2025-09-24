package com.example.blogplatform.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class GenericResponse<T> {

    private boolean success;
    private LocalDateTime timestamp;
    private String message;
    private T data;

    public GenericResponse(boolean success, String message, T data) {
        this.success = success;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<GenericResponse<T>> success(String message, HttpStatus httpStatus, T data) {
        return ResponseEntity
                .status(httpStatus)
                .body(new GenericResponse<>(true, message, data));
    }

    public static <T> ResponseEntity<GenericResponse<T>> error(String message, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new GenericResponse<>(false, message, null));
    }

}
